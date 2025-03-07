import React from 'react';
import { Bar } from 'react-chartjs-2';
import { Chart as ChartJS, CategoryScale, LinearScale, BarElement, Title, Tooltip, Legend } from 'chart.js';
import { Trophy } from 'lucide-react';

ChartJS.register(CategoryScale, LinearScale, BarElement, Title, Tooltip, Legend);

const BarGraph = ({ easy, medium, hard }) => {
  // Maximum values for each difficulty level
  const maxValues = {
    easy: 50,
    medium: 30,
    hard: 20,
  };

  const data = {
    labels: ['Easy', 'Medium', 'Hard'],
    datasets: [
      {
        label: 'Problems Solved',
        data: [easy, medium, hard],
        backgroundColor: [
          'rgba(34, 197, 94, 0.8)', // green-500
          'rgba(234, 179, 8, 0.8)',  // yellow-500
          'rgba(239, 68, 68, 0.8)',  // red-500
        ],
        borderColor: [
          'rgb(22, 163, 74)',  // green-600
          'rgb(202, 138, 4)',  // yellow-600
          'rgb(220, 38, 38)',  // red-600
        ],
        borderWidth: 1,
        borderRadius: 6,
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
        ticks: {
          font: {
            family: 'Inter, system-ui, sans-serif',
          },
        },
      },
      y: {
        grid: {
          display: false,
        },
        ticks: {
          font: {
            family: 'Inter, system-ui, sans-serif',
            weight: '500',
          },
        },
      },
    },
    plugins: {
      legend: {
        display: false,
      },
      tooltip: {
        backgroundColor: 'rgba(17, 24, 39, 0.8)',
        padding: 12,
        titleFont: {
          family: 'Inter, system-ui, sans-serif',
          size: 14,
        },
        bodyFont: {
          family: 'Inter, system-ui, sans-serif',
          size: 13,
        },
        callbacks: {
          label: (context) => {
            const value = context.raw;
            const maxValue = maxValues[context.label.toLowerCase()];
            return `${value}/${maxValue} completed`;
          },
        },
      },
    },
    maintainAspectRatio: false,
  };

  const totalSolved = easy + medium + hard;
  const totalMax = maxValues.easy + maxValues.medium + maxValues.hard;
  const completionPercentage = Math.round((totalSolved / totalMax) * 100);

  return React.createElement(
    'div',
    { className: 'bg-white rounded-xl shadow-lg p-6 w-full max-w-md' },
    React.createElement(
      'div',
      { className: 'flex items-center justify-between mb-6' },
      React.createElement(
        'div',
        null,
        React.createElement('h2', { className: 'text-xl font-bold text-gray-800' }, 'Solved Problems'),
        React.createElement('p', { className: 'text-sm text-gray-500 mt-1' }, 'Track your progress')
      ),
      React.createElement(
        'div',
        { className: 'flex items-center bg-purple-50 px-3 py-2 rounded-lg' },
        React.createElement(Trophy, { className: 'w-5 h-5 text-purple-500 mr-2' }),
        React.createElement('span', { className: 'text-purple-700 font-semibold' }, `${completionPercentage}%`)
      )
    ),
    React.createElement(
      'div',
      { className: 'h-48' },
      React.createElement(Bar, { data, options })
    ),
    React.createElement(
      'div',
      { className: 'grid grid-cols-3 gap-4 mt-6' },
      ['easy', 'medium', 'hard'].map((level, index) =>
        React.createElement(
          'div',
          { key: index, className: 'text-center' },
          React.createElement(
            'p',
            { className: `${level === 'easy' ? 'text-green-600' : level === 'medium' ? 'text-yellow-600' : 'text-red-600'} font-semibold` },
            `${eval(level)}/${maxValues[level]}`
          ),
          React.createElement('p', { className: 'text-sm text-gray-500' }, level.charAt(0).toUpperCase() + level.slice(1))
        )
      )
    )
  );
};

export default BarGraph;
