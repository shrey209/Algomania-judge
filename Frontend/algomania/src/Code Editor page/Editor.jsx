import React, { useState } from 'react';
import Dropdown from './Dropdown';
import UndoButton from './UndoButton';
import EditorComponent from './EditorComponent';
import 'ace-builds/src-noconflict/mode-c_cpp';
import 'ace-builds/src-noconflict/mode-python';
import 'ace-builds/src-noconflict/theme-monokai';
import InputOutputBoxes from './InputOutputBoxes';
import Darkmodebutton from './Darkmodebutton';

const Editor = () => {
  const [lang, setLang] = useState("cpp");
  const [code, setCode] = useState(""); 
  const [darkMode, setDarkMode] = useState(false);
  const [input, setInput] = useState('');
  const [isLoading, setLoading] = useState(false);
  const [output, setOutput] = useState('');
  const [error, setError] = useState(false);

  const toggleDarkMode = () => {
    setDarkMode(!darkMode);
  };

  const handleUndo = () => {
    setCode('');
  };

  const handleRun = () => {
    setLoading(true); // Set loading state when run button is clicked

    let uuid = ''; // Local variable to store UUID

    const socket = new WebSocket('ws://localhost:8080/ws');

    socket.onopen = () => {
      console.log('Connected to the WebSocket server.');
      setLoading(true);
    };

    socket.onmessage = (event) => {
      const message = event.data;
      console.log('Message from server:', message);
      handleTextMessage(message);
    };

    socket.onclose = () => {
      console.log('Disconnected from the WebSocket server.');
      setLoading(false);
    };

    socket.onerror = (error) => {
      console.error('WebSocket error:', error);
      setError(true);
      setLoading(false);
    };

    const handleTextMessage = (message) => {
      console.log('Received message from server:', message);

      if (message.startsWith('Your UUID is: ')) {
        const newUuid = message.substring('Your UUID is: '.length);
        uuid = newUuid.replace(/\s/g, ''); // Set local variable uuid
        console.log('UUID set:', uuid);
        sendCodeRequest(uuid); // Once UUID is received, send code request with uuid
      } else {
        try {
          const codeResponse = JSON.parse(message);

          if (codeResponse.hasOwnProperty('isError') && codeResponse.hasOwnProperty('response')) {
            console.log('Parsed CodeResponse:', codeResponse);
            setOutput(codeResponse.response);
          } else {
            console.log('Received unrecognized JSON-like data:', codeResponse);
            // Handle unexpected JSON structure or other cases
          }
        } catch (error) {
          console.log('Message is not JSON-like. Handling as plain text:', message);
          setOutput(message); // Handle plain text messages
        }
      }
    };

    const sendCodeRequest = (uuid) => {
      const message = {
        uuid: uuid, 
        code: code,
        input_data: input,
        lang: lang
      };

      const jsonString = JSON.stringify(message);

      if (socket.readyState === WebSocket.OPEN) {
        socket.send(jsonString);
        console.log('Message sent to server:', message);
      } else {
        console.error('WebSocket connection is not open. Message not sent.');
      }
    };
  };

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

  return (
    <div className={`min-h-screen ${darkMode ? 'bg-gray-900 text-white' : 'bg-gray-100 text-gray-800'}`}>
      {/* Header */}
      <div className="flex justify-start p-4 mb-10">
        <div className="relative inline-block text-3xl font-bold text-purple-500">
          <span className="bg-gradient-to-r from-purple-400 to-purple-600 bg-clip-text text-transparent">
            Algomania
          </span>
          <span className="text-gray-700 dark:text-gray-300 ml-2">
            Code Editor
          </span>
        </div>
      </div>

      {/* Main Content */}
      <div className={`grid grid-cols-2 gap-4 px-4 lg:px-10 ${darkMode ? 'bg-gray-800' : 'bg-gray-200'} transition-all duration-300`}>
        {/* Editor Section */}
        <div>
          <div className={`p-4 flex justify-between items-center rounded-t shadow-md ${darkMode ? 'bg-gray-700 border-gray-600' : 'bg-gray-300 border-gray-300'} transition-all duration-300`}>
            <div className="flex items-center space-x-5">
              <Dropdown lang={lang} setLang={setLang} />
              <UndoButton onClick={handleUndo} />
              <Darkmodebutton darkMode={darkMode} setDarkMode={setDarkMode} />
            </div>
            <button
              className="bg-green-500 hover:bg-green-600 text-white px-5 py-2 rounded shadow"
              onClick={handleRun}
              disabled={isLoading}
            >
              {renderButtonContent()}
            </button>
          </div>

          <div className="mt-0">
            <EditorComponent code={code} setCode={setCode} lang={lang} darkMode={darkMode} />
          </div>
        </div>

        {/* Input/Output Section */}
        <div>
          <InputOutputBoxes darkMode={darkMode} input={input} setInput={setInput} output={output} setOutput={setOutput} />
        </div>
      </div>
    </div>
  );
};

export default Editor;
