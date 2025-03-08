import React from "react";
import { MdCancel } from "react-icons/md";

const Output = ({ setDropdownOpen, response }) => {
  const handleOnClick = () => {
    setDropdownOpen(false); // Close the dropdown or hide the Output component
  };

  return (
    <div className="absolute bottom-0 left-0 right-0 bg-white shadow-md z-10 p-6 rounded-lg border border-gray-200">
      {/* Close button positioned in the top-right corner */}
      <div className="flex justify-end">
        <button onClick={handleOnClick} className="text-gray-500 hover:text-gray-700 transition duration-300">
          <MdCancel size={24} />
        </button>
      </div>

      {/* Output content */}
      <div className="mt-4">
        {response.accepted ? (
          // Show a green check if accepted
          <div className="text-green-600 text-lg font-semibold">✅ It works!</div>
        ) : response.error ? (
          // Show response if there is an error
          <div className="mt-4">
            <textarea
              className="w-full p-3 border border-red-600 text-red-600 rounded-md bg-red-50"
              rows={6}
              value={response.response}
              readOnly
            />
          </div>
        ) : (
          // Show expected vs. response if not accepted and no error
          <div className="mt-4">
            <div className="text-lg font-semibold">
              <span className="text-purple-600">Expected:</span>
            </div>
            <textarea
              className="w-full p-3 border border-gray-300 rounded-md bg-gray-50"
              rows={4}
              value={response.expected}
              readOnly
            />
            <div className="text-lg font-semibold mt-2">
              <span className="text-red-600">Your Output:</span>
            </div>
            <textarea
              className="w-full p-3 border border-red-600 rounded-md bg-red-50"
              rows={4}
              value={response.response}
              readOnly
            />
          </div>
        )}
      </div>
    </div>
  );
};

export default Output;
