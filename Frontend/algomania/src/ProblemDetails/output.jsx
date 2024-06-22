import React from 'react';
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
        <div className="text-lg font-semibold">
          <span className="text-purple-600 transition-colors duration-300">Status:</span> {response.isAccepted ? (
            <span className="text-green-600">Accepted</span>
          ) : (
            <span className="text-red-600">Not Accepted</span>
          )}
        </div>
      </div>

      {/* Error message textarea */}
      {response.isError && (
        <div className="mt-4">
          <textarea
            className="w-full p-3 border border-red-600 text-red-600 rounded-md bg-red-50"
            rows={6} // Set to a good length, adjust as necessary
            value={response.error}
            readOnly
          />
        </div>
      )}
    </div>
  );
};

export default Output;
