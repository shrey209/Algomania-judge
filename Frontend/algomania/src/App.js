import React from 'react';
import { BrowserRouter, Routes, Route } from 'react-router-dom';

import Explore from './components/Explore';
import Problems from './Problems page/problems';
import Contest from './components/contest';
import Home from './Home page/Home';
import Editor from './Code Editor page/Editor';
import ProblemDetails from './ProblemDetails/ProblemDetails';

import Dashboard from './Dashboard/Dashboard';
import { AuthProvider } from "./context/context";

function App() {
  return (
    <BrowserRouter>
      <AuthProvider>
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/Explore" element={<Explore />} />
          <Route path="/Problems" element={<Problems />} />
          <Route path="/Contest" element={<Contest />} />
          <Route path="/Editor" element={<Editor />} />
          <Route path="/problemDetails/:id" element={<ProblemDetails />} />
          <Route path="/Dashboard" element={<Dashboard />} />
        </Routes>
      </AuthProvider>
    </BrowserRouter>
  );
}

export default App;
