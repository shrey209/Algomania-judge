import React, { useState, useEffect } from 'react';
import { useAuth } from '../context/context';
import UserComponent from './UserComponenet'; 
import Bargraph from './Bargraph';
import Smallcard from './Smallcard';
import Leaderboard from './Leaderboard';
import axios from 'axios';
import Solved from './solved';


const Dashboard = () => {
  const { userid, jwt , setUserinformation} = useAuth();
  const [userinfo, setUserinfo] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchUserInfo = async () => {
      try {
        const response = await axios.get(`http://localhost:8080/users/${userid}`, {
          headers: {
            Authorization: `Bearer ${jwt}`
          }
        });
        setUserinfo(response.data);
        setUserinformation(response.data);
      } catch (error) {
        console.error('Error fetching user information:', error);
      } finally {
        setLoading(false);
      }
    };
  
    fetchUserInfo(); // Call the function when component mounts or userid/jwt changes
  }, [userid, jwt]); // useEffect dependencies: userid and jwt

  if (loading) {
    return <div>Loading...</div>;
  }

  return (
    <div>
      <div className="grid grid-cols-5 min-h-screen">
        {/* User info div */}
        <div className="bg-gradient-to-b from-purple-400 to-purple-800 p-4">
          <UserComponent userinfo={userinfo} />
        </div>

        {/* Second column with 4/5 width */}
        <div className="col-span-2 flex flex-col">
          <div className="flex-1 flex items-center justify-center">
            <div className="relative w-full">
              <Bargraph easy={userinfo.easyCount} hard={userinfo.hardCount} medium={userinfo.mediumCount} />
              <div className="absolute top-0 right-0 h-full flex flex-col justify-center space-y-4 p-4">
                <Smallcard points={10} />
                <Smallcard points={20} />
              </div>
            </div>
          </div>
          <div className="flex-1">
            <Solved />
          </div>
        </div>

        <div className="col-span-2">
          <div>
            <Leaderboard />
          </div>
        </div>
      </div>
    </div>
  );
};

export default Dashboard;
