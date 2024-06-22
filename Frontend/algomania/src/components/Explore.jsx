import React, { useState, useEffect } from 'react';

const CircleProgressBar = ({ defaultPercentage }) => {
    const [percentage, setPercentage] = useState(defaultPercentage);

    const circleSize = 120; // Size of the circle in pixels
    const strokeWidth = 10; // Width of the border in pixels
    const radius = (circleSize - strokeWidth) / 2; // Radius of the circle

    // Calculate the circumference of the circle
    const circumference = 2 * Math.PI * radius;

    // Calculate the stroke-dasharray property to represent the percentage
    const strokeDasharray = circumference;
    const strokeDashoffset = circumference - ((percentage / 100) * circumference);

    // Function to update percentage (for testing purposes)
    const updatePercentage = () => {
        // Example: Change percentage after 2 seconds (for testing)
        setTimeout(() => {
            setPercentage(50); // Change percentage to 50% after 2 seconds
        }, 2000);
    };

    // Call updatePercentage function on component mount (for testing purposes)
    useEffect(() => {
        updatePercentage();
    }, []);

    return (
        <svg className="block" width={circleSize} height={circleSize} viewBox={`0 0 ${circleSize} ${circleSize}`} fill="none" xmlns="http://www.w3.org/2000/svg">
            <circle cx={circleSize / 2} cy={circleSize / 2} r={radius} stroke="#ddd" strokeWidth={strokeWidth} fill="transparent" />
            <circle cx={circleSize / 2} cy={circleSize / 2} r={radius} stroke="#4CAF50" strokeWidth={strokeWidth} fill="transparent"
                strokeDasharray={strokeDasharray} strokeDashoffset={strokeDashoffset} strokeLinecap="round" transform={`rotate(-90 ${circleSize / 2} ${circleSize / 2})`} />
            <text x="50%" y="50%" textAnchor="middle" dominantBaseline="middle" fontSize="20" fill="#333">{percentage}%</text>
        </svg>
    );
};

const Explore = () => {
    return (
        <div>
            <CircleProgressBar defaultPercentage={70} />
        </div>
    );
};

export default Explore;
