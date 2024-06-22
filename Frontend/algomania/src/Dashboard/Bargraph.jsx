import React from 'react';
import { Bar } from 'react-chartjs-2';
import { Chart as ChartJS, CategoryScale, LinearScale, BarElement, Title, Tooltip, Legend } from 'chart.js';

ChartJS.register(CategoryScale, LinearScale, BarElement, Title, Tooltip, Legend);

const Bargraph = ({ easy, medium, hard }) => {
  // Maximum values for easy, medium, and hard
  const maxValues = {
    easy: 200,
    medium: 100,
    hard: 50,
  };

  const data = {
    labels: ['Easy', 'Medium', 'Hard'],
    datasets: [
      {
        label: 'Levels',
        data: [easy, medium, hard],
        backgroundColor: ['green', 'yellow', 'red'],
      },
    ],
  };

 
  const options = {
    indexAxis: 'y', 
    scales: {
      x: {
        beginAtZero: true,
        max: Math.max(maxValues.easy, maxValues.medium, maxValues.hard),
        grid: {
          display: false, 
        },
      },
      y: {
        grid: {
          display: false,
        },
      },
    },
  };

  return (
    <div className="bg-gray-100 py-4">
      <div className="max-w-sm ml-4  bg-white shadow-lg rounded-lg overflow-hidden">
        <div className="p-5 pt-4 flex justify-start items-center"> {/* Adjusted padding and flexbox classes */}
          <h5 className="text-xl font-bold mb-4 text-purple-500">Solved Problems</h5>
        </div>
        <div className="p-5 pt-0"> {/* Adjusted padding */}
          <Bar data={data} options={options} />
        </div>
      </div>
    </div>
  );
};


export default Bargraph;
