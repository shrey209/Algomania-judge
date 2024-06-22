import { useState, useEffect } from 'react';
import { Prism as SyntaxHighlighter } from 'react-syntax-highlighter';
import { vscDarkPlus } from 'react-syntax-highlighter/dist/esm/styles/prism'; // Import a dark theme

const codeExample = `// Welcome to Algomania Code Editor!
#include <iostream>

int main() {
    std::cout << "Hello, World!" << std::endl;
    return 0;
}
`;

const CodeEditor = () => {
    const [code, setCode] = useState('');
    const [isTyping, setIsTyping] = useState(true);

    useEffect(() => {
        let currentIndex = 0;

        const typeCode = () => {
            if (currentIndex < codeExample.length) {
                setCode((prev) => prev + codeExample[currentIndex]);
                currentIndex++;
            } else {
                setIsTyping(false);
            }
        };

        const typingInterval = setInterval(typeCode, 50);

        return () => clearInterval(typingInterval);
    }, []);

    return (
        <div className="max-w-sm mx-auto mt-8">
            <div className="flex justify-between items-center mb-2">
                <h2 className="text-lg font-semibold text-gray-800">Code Editor</h2>
                <button
                    className="bg-purple-500 hover:bg-purple-600 text-white px-3 py-1 rounded-md text-sm"
                    onClick={() => alert('Code Submitted!')}
                >
                    Submit
                </button>
            </div>
            <div className="bg-gray-900 rounded-md p-2 max-h-96 overflow-y-auto">
                <SyntaxHighlighter language="cpp" style={vscDarkPlus} className="rounded-md text-xs">
                    {code}
                </SyntaxHighlighter>
            </div>
        </div>
    );
};

export default CodeEditor;
