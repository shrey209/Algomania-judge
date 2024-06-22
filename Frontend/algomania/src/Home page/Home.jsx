import React from 'react';
import Navbar from './Navbar';
import CodeEditor from './CodeEditor';

const Home = () => {
  return (
    <>
      <Navbar />
      <div className="flex min-h-screen bg-gray-100">
        <div className="flex-grow p-4">
          <section className="hero-section bg-purple-500 text-white p-8 rounded-lg mb-8">
            <h1 className="text-4xl font-bold mb-4">Welcome to Algomania!</h1>
            <p className="text-lg mb-4">Solve coding problems, compete in contests, and improve your skills.</p>
            <button className="bg-white text-purple-500 px-6 py-2 rounded-lg font-semibold">Get Started</button>
          </section>

          <section className="features-section mb-8">
            <h2 className="text-2xl font-semibold mb-4">Features</h2>
            <div className="grid grid-cols-1 md:grid-cols-3 gap-4">
              <div className="p-4 feature bg-white p-4 rounded-lg shadow">
                <h3 className=" font-semibold text-xl mb-2">Solve Problems</h3>
                <p className="text-gray-700">Practice coding problems and improve your skills.</p>
              </div>
              <div className="feature bg-white p-4 rounded-lg shadow">
                <h3 className="font-semibold text-xl mb-2">Compete in Contests</h3>
                <p className="text-gray-700">Participate in regular contests and challenges.</p>
              </div>
              <div className="feature bg-white p-4 rounded-lg shadow">
                <h3 className="font-semibold text-xl mb-2">Track Your Progress</h3>
                <p className="text-gray-700">Monitor your progress and see your improvement over time.</p>
              </div>
            </div>
          </section>

          <section className="popular-problems-section mb-8">
            <h2 className="text-2xl font-semibold mb-4">Popular Problems</h2>
            <ul>
              <li className="bg-white p-4 rounded-lg shadow mb-4">
                <h3 className="font-semibold text-xl">Two Sum</h3>
                <p className="text-gray-700">Find two numbers that add up to a specific target.</p>
              </li>
              <li className="bg-white p-4 rounded-lg shadow mb-4">
                <h3 className="font-semibold text-xl">Reverse Linked List</h3>
                <p className="text-gray-700">Reverse a singly linked list.</p>
              </li>
              <li className="bg-white p-4 rounded-lg shadow mb-4">
                <h3 className="font-semibold text-xl">Longest Substring Without Repeating Characters</h3>
                <p className="text-gray-700">Find the longest substring without repeating characters.</p>
              </li>
            </ul>
          </section>
          
          <section className="testimonials-section mb-8">
            <h2 className="text-2xl font-semibold mb-4">What Our Users Say</h2>
            <div className="testimonial bg-white p-4 rounded-lg shadow mb-4">
              <p className="text-gray-700">"Algomania has been instrumental in helping me improve my coding skills. The problems are well-curated and challenging."</p>
              <p className="text-gray-800 font-semibold mt-2">- John Doe</p>
            </div>
            <div className="testimonial bg-white p-4 rounded-lg shadow mb-4">
              <p className="text-gray-700">"The contests are really fun and competitive. I look forward to them every week!"</p>
              <p className="text-gray-800 font-semibold mt-2">- Jane Smith</p>
            </div>
          </section>
          
          <section className="call-to-action-section mb-8">
            <h2 className="text-2xl font-semibold mb-4">Ready to Get Started?</h2>
            <button className="bg-purple-500 text-white px-6 py-2 rounded-lg font-semibold">Sign Up Now</button>
          </section>
        </div>
        <div className="flex justify-end w-1/3 p-4">
          <CodeEditor />
        </div>
      </div>
    </>
  );
};

export default Home;
