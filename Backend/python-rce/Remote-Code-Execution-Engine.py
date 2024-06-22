import os
from fastapi import FastAPI, HTTPException
from pydantic import BaseModel
import uuid

from CodeRunner_cpp_c import create_and_run_cpp_container
from CodeRunner_python import create_and_run_python_container
from FiledbCalls import get_file_data

app = FastAPI()

class SubmitJudgeDTO(BaseModel):
    code: str
    lang: str
    problem_id: int

class SubmitJudgeResponse(BaseModel):
    isError: bool
    isAccepted: bool
    error: str    

@app.post("/SubmitCode", response_model=SubmitJudgeResponse)  
async def code_runner(submitjudgeDTO: SubmitJudgeDTO):
    random_uuid = str(uuid.uuid4())
    base_dir = os.path.dirname(os.path.abspath(__file__))  

    # Determine file extensions and filenames
    code_extension = "cpp" if submitjudgeDTO.lang in ["cpp", "c"] else "py"
    code_filename = f"{random_uuid}.{code_extension}"
    input_filename = f"{random_uuid}.txt"
    
    # Retrieve file data from external service
    file_data = get_file_data(submitjudgeDTO.problem_id)
    
    if "error" in file_data:
        raise HTTPException(status_code=400, detail=file_data["error"])

    # Write code and input data to files
    code_file_path = os.path.join(base_dir, code_filename)
    input_file_path = os.path.join(base_dir, input_filename)

    try:
        with open(code_file_path, 'w') as code_file:
            code_file.write(submitjudgeDTO.code)

        with open(input_file_path, 'w') as input_file:
            input_file.write(file_data["inputfiledata"])

        # Running the containers according to language specified
        if submitjudgeDTO.lang == "cpp":
            output, error = create_and_run_cpp_container(code_file_path, input_file_path)
        elif submitjudgeDTO.lang == "python":
            output, error = create_and_run_python_container(code_file_path, input_file_path)    
        else:
            raise HTTPException(status_code=400, detail="Unsupported language specified")

        is_error = bool(error.strip())

        # Compare output with expected data
        if is_error:
            response_dto = SubmitJudgeResponse(
                isError=True,
                isAccepted=False,
                error=error
            )
        elif file_data["outputfiledata"] != output:
            response_dto = SubmitJudgeResponse(
                isError=False,
                isAccepted=False,
                error=''
            )
        else:
            response_dto = SubmitJudgeResponse(
                isError=False,
                isAccepted=True,
                error=''
            )
    finally:
        # Delete temporary files regardless of outcome
        if os.path.exists(code_file_path):
            os.remove(code_file_path)
        if os.path.exists(input_file_path):
            os.remove(input_file_path)

    return response_dto




class CodeInput(BaseModel):
    code: str
    input_data: str
    lang: str

class ResponseDTO(BaseModel):
    isError: bool
    response: str

@app.post("/code", response_model=ResponseDTO)
async def code_runner(code_input: CodeInput):
    random_uuid = str(uuid.uuid4())

    base_dir = os.path.dirname(os.path.abspath(__file__))  #find the path where the codes running

    code_extension = "cpp" if code_input.lang in ["cpp", "c"] else "py"
    code_filename = f"{random_uuid}.{code_extension}"
    input_filename = f"{random_uuid}.txt"
    output_filename = f"{random_uuid}_output.txt"
    error_filename = f"{random_uuid}_error.txt"
    
    code_file_path = os.path.join(base_dir, code_filename)
    input_file_path = os.path.join(base_dir, input_filename)
    output_file_path = os.path.join(base_dir, output_filename)
    error_file_path = os.path.join(base_dir, error_filename)

 
    with open(code_file_path, 'w') as code_file:    # Create the code file
        code_file.write(code_input.code)

   
    with open(input_file_path, 'w') as input_file:    # Create the input file
        input_file.write(code_input.input_data)

    # Running the containers according to language specefied
    if code_input.lang in ["cpp", "c"]:
        output, error = create_and_run_cpp_container(code_file_path, input_file_path, output_file_path, error_file_path)
    elif code_input.lang == "python":
        output, error = create_and_run_python_container(code_file_path, input_file_path, output_file_path, error_file_path)   
    else:
        return ResponseDTO(isError=True, response="Unsupported language specified")

   
    is_error = bool(error.strip()) 


    response_dto = ResponseDTO(
        isError=is_error,
        response=error if is_error else output
    )

    # del all fiels
    os.remove(code_file_path)
    os.remove(input_file_path)
    os.remove(output_file_path)
    os.remove(error_file_path)

    return response_dto


if __name__ == "__main__":
    import uvicorn
    uvicorn.run(app, host="127.0.0.1", port=8013)
