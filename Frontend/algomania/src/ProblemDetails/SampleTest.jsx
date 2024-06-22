import React from 'react';

const SampleTest = ({ id, input, output, explanation }) => {
  return (
    <div>
      <div className="pl-4">
        <div className="my-4 text-xl font-semibold">Example {id} :</div>
        <div className="bg-gray-300">
          <div className="p-2">
            <span className="font-semibold text-base">Input : </span>
            {input}
            <span className=""></span>
          </div>
          <div className="p-2">
            <span className="font-semibold text-base">Output: </span>
            {output}
          </div>
          <div className="p-2">
            <span className="text-base font-semibold">Explanation : </span>
            {explanation}
          </div>
        </div>
      </div>
    </div>
  );
};

export default SampleTest;
