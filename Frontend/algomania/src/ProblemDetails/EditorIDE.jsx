import React, { useState } from 'react';
import AceEditor from 'react-ace';
import { IoIosArrowDropup, IoIosArrowDropdown } from 'react-icons/io'; 
import Output from './output'; // Import your Output component
// Import AceEditor modes and theme
import 'ace-builds/src-noconflict/mode-c_cpp';
import 'ace-builds/src-noconflict/mode-python';
import 'ace-builds/src-noconflict/theme-monokai';

const EditorIDE = ({ problemDetailsId }) => {
  const [selectedLang, setSelectedLang] = useState('cpp');
  const [code, setCode] = useState('');
  const [isLoading, setLoading] = useState(false);
  const [isDropdownOpen, setDropdownOpen] = useState(false); 
  const [response, setResponse] = useState({}); // Initial state based on SubmitCodeResponse structure

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

  const handleRun = () => {
    setLoading(true);
    
    const data = {
      userid: "66712d741802481c4320d326",  
      code: code,
      lang: selectedLang,
      problemId: problemDetailsId  
    };

    const jsonData = JSON.stringify(data);

    const socket = new WebSocket('ws://localhost:9001/submit');

    socket.onopen = () => {
      console.log('Connected to the WebSocket server.');
      socket.send(jsonData);
    };

    socket.onerror = (error) => {
      console.error('WebSocket Error:', error);
      setLoading(false); 
    };

    socket.onmessage = (event) => {
      console.log('Message from server:', event.data);
      try {
        const parsedData = JSON.parse(event.data); // Parse JSON data
        setResponse(parsedData); // Update response state with parsed data
        setDropdownOpen(true);
      } catch (error) {
        console.error('Error parsing JSON:', error);
      }
    };

    socket.onclose = () => {
      console.log('WebSocket connection closed.');
      setLoading(false); 
    };
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
          placeholder={`Write your ${selectedLang === 'cpp' ? 'C++' : 'Python'} code here...`}
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
