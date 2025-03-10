import React, { useState, useEffect } from 'react';
import { useParams, useLocation } from 'react-router-dom';
import getProblemDetailsById from '../Services/ProblemDetailService';
import SampleTest from './SampleTest';
import EditorIDE from './EditorIDE';
import { LoadingSpinner } from '../components/Loading';


const ProblemDetails = () => {

  const { id } = useParams();
  const location = useLocation();
  const { problemDetailsId = '', difficulty = '', category = '' ,title=''} = location.state || {};

  const [problemData, setProblemData] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchProblemData = async () => {
      try {
        console.log("problemDetailsId:", problemDetailsId);

        const data = await getProblemDetailsById(problemDetailsId);
        console.log(data);
        setProblemData(data);
      } catch (error) {
        setError(error);
      } finally {
        setLoading(false);
      }
    };

    fetchProblemData();
  }, [problemDetailsId]);

  if (loading) {
    return   <div className="min-h-screen bg-gradient-to-br from-purple-50 to-white flex items-center justify-center">
    <div className="bg-white p-8 rounded-xl shadow-lg border border-purple-100">
      <LoadingSpinner />
    </div>
  </div>
  }

  if (error) {
    return <div>Error: {error.message}</div>;
  }

  return (
    <div className="grid grid-cols-2 bg-gray-200">
  <div className="relative p-4 overflow-y-auto" style={{ maxHeight: "calc(100vh - 2rem)" }}>
    <div className="bg-gray-100 p-4 rounded-lg shadow-sm">
      <span className="text-purple-500 text-xl font-bold">{problemData.id}.{title}</span>
      <div className="m-3">
        <div className="flex space-x-6 text-base">
          <span>
            Difficulty: <span className="text-emerald-500 font-semibold">{difficulty}</span>
          </span>
          <span>
            Category: <span className="text-purple-500 font-semibold">{category}</span>
          </span>
        </div>
      </div>
    </div>
    <div className="p-4 flex justify-between items-center">
      <span className="text-xl font-semibold">Description:</span>
      <button className="bg-blue-500 text-white px-4 py-2 rounded shadow-sm hover:bg-blue-600">
        Solution
      </button>
    </div>
    <div className="pl-4 text-gray-800">
      {problemData.description}
    </div>
    
    <div>
  {problemData.sampleTestCases.map(testcase => (
    <SampleTest
      id={testcase.id}
      input={testcase.input}
      output={testcase.output}
      explanation={testcase.explanation}
    />
  ))}
  <div className="mt-4 p-4">
   <span className="text-xl font-semibold  "  >Constraints : </span> 
   <div className="text-gray-800 mt-2 "> {problemData.constraints}</div>
  
  </div>

  
</div>

  </div>
  {/* right content */}
  <div className="min-h-screen">
   
   <EditorIDE problemDetailsId={problemData.id}></EditorIDE>
   

  </div>
</div> 
  );
};

export default ProblemDetails;
  {/*
    {
  "id": 0,
  "categoryId": 0,
  "solutionId": 0,
  "description": "string",
  "constraints": "string",
  "sampleTestCases": [
    {
      "id": 0,
      "input": "string",
      "output": "string",
      "explanation": "string",
      "problemDetails": "string"
    }
  ]
}
    */}
    