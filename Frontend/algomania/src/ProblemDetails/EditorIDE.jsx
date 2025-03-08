import React, { useState } from 'react';
import AceEditor from 'react-ace';
import { IoIosArrowDropup, IoIosArrowDropdown } from 'react-icons/io'; 
import Output from './output'; // Import your Output component
// Import AceEditor modes and theme
import axios from "axios";
import 'ace-builds/src-noconflict/mode-c_cpp';
import 'ace-builds/src-noconflict/mode-python';
import 'ace-builds/src-noconflict/theme-monokai';
import { BASE_URL } from "../constant";

const EditorIDE = ({ problemDetailsId }) => {
  const [selectedLang, setSelectedLang] = useState('C++');
  const [code, setCode] = useState('');
  const [isLoading, setLoading] = useState(false);
  const [isDropdownOpen, setDropdownOpen] = useState(false); 
  const [response, setResponse] = useState({}); 

  const renderButtonContent = () => {
    if (isLoading) {
      return (
        <div
          className="inline-block h-5 w-5 animate-spin rounded-full border-4 border-solid border-current border-r-transparent align-[-0.125em] motion-reduce:animate-[spin_1.5s_linear_infinite]"
          role="status"
        >
          <span className="sr-only">Loading...</span>
        </div>
      );
    } else {
      return 'Run';
    }
  };

  const handleLangChange = (e) => {
    setSelectedLang(e.target.value);
  };

  const handleRun = async () => {
    setLoading(true);
  
    try {
      const payload = {
        problem_id: problemDetailsId, // Ensure problemDetailsId is passed as a prop
        code: code,
        lang: selectedLang === "cpp" ? "C++" : "Python", // Convert to correct format
      };
      console.log(payload)
      const response = await axios.post(`${BASE_URL}/Algomania/Execute/code`, payload, {
        headers: { "Content-Type": "application/json" }, // Ensure JSON format
      });
  
      setResponse(response.data); 
      console.log(response.data);
      setDropdownOpen(true);
    } catch (error) {
      console.error("Execution failed:", error);
    }
  
    setLoading(false);
  };

  const toggleDropdown = () => {
    setDropdownOpen(!isDropdownOpen);
  };

  return (
    <div className="relative"> {/* Relative positioning for the parent container */}
      <div className="bg-white shadow-md">
        <div className="p-4 flex items-center mb-4">
          <label htmlFor="language" className="mr-4">Language:</label>
          <select
            id="language"
            className="p-2 border border-gray-300 rounded-md"
            value={selectedLang}
            onChange={handleLangChange}
          >
            <option value="cpp">C++</option>
            <option value="python">Python</option>
          </select>
        </div>
      </div>

      <div className="relative"> {/* Relative positioning for AceEditor and children */}
        <AceEditor
          mode={selectedLang === 'cpp' ? 'c_cpp' : 'python'}
          theme="monokai"
          fontSize={14}
          showPrintMargin={true}
          showGutter={true}
          highlightActiveLine={true}
          width="100%"
          height="500px"
          value={code}
          onChange={setCode}
          placeholder={`Write your ${selectedLang === 'C++' ? 'C++' : 'Python'} code here...`}
          setOptions={{
            enableBasicAutocompletion: true,
            enableLiveAutocompletion: true,
            enableSnippets: true,
            showLineNumbers: true,
            tabSize: 2,
          }}
          editorProps={{ $blockScrolling: true }}
        />
        {isDropdownOpen && <Output setDropdownOpen={setDropdownOpen} response={response} />} {/* Render Output component if dropdown is open */}
      </div>

      <div className="flex flex-col bg-white h-full shadow-md">
        <div className="flex justify-between items-center bg-gray-200 px-4 py-2">
          <button
            className="bg-blue-500 text-white px-6 py-3 rounded-lg shadow-md hover:bg-blue-600"
            onClick={handleRun}
          >
            { renderButtonContent() }
          </button>
          <div onClick={toggleDropdown} className="cursor-pointer text-purple-600 text-4xl">
            { isDropdownOpen ? <IoIosArrowDropup /> : <IoIosArrowDropdown /> }
          </div>
          <h2 className="text-xl font-semibold">Algomania</h2>
        </div>
      </div>
    </div>
  );
};

export default EditorIDE;
