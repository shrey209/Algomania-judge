import requests
import base64

def get_file_data(problem_id):
    # URL of the endpoint
    url = f"http://localhost:8000/Algomania/files/problem/{problem_id}"
    
    # Sending GET request to the endpoint
    response = requests.get(url)
    
    # Check if the request was successful
    if response.status_code == 200:
        # Getting the response data
        filedb = response.json()
        
        # Checking if inputfiledata and outputfiledata exist in the response
        if "inputfiledata" in filedb and "outputfiledata" in filedb:
            inputfiledata = filedb["inputfiledata"]
            outputfiledata = filedb["outputfiledata"]
            
            # Decode inputfiledata and outputfiledata
            decoded_inputfiledata = base64.b64decode(inputfiledata).decode("utf-8")
            decoded_outputfiledata = base64.b64decode(outputfiledata).decode("utf-8").replace('\r', '')
            
            # Return inputfiledata and outputfiledata as a dictionary
            return {
                "inputfiledata": decoded_inputfiledata,
                "outputfiledata": decoded_outputfiledata
            }
        else:
            return {"error": "Input file data or output file data not found."}
    else:
        return {"error": f"Failed to retrieve file data. Status code: {response.status_code}"}

# problem_id = 1
# file_data = get_file_data(problem_id)
# print(file_data)