import React from 'react';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faUser, faIdBadge, faCheckCircle } from '@fortawesome/free-solid-svg-icons';
import Avatar from './Avatar';

const UserComponent = ({ userinfo }) => {
  if (!userinfo) {
    return <div>Loading...</div>;
  }

  return (
    <div className="text-white">
      <div className="flex justify-center mb-4">
        <Avatar />
      </div>
      <div className="text-gray-100">
        <div className="flex items-center mb-2 border-b border-white pb-2">
          <FontAwesomeIcon icon={faUser} className="mr-2 text-blue-400" />
          <span className="font-bold">Username:</span>
          <span className="ml-2">{userinfo.username}</span>
        </div>
        <div className="flex items-center mb-2 border-b border-white pb-2">
          <FontAwesomeIcon icon={faIdBadge} className="mr-2 text-green-400" />
          <span className="font-bold">First Name:</span>
          <span className="ml-2">{userinfo.firstname}</span>
        </div>
        <div className="flex items-center mb-2 border-b border-white pb-2">
          <FontAwesomeIcon icon={faIdBadge} className="mr-2 text-green-400" />
          <span className="font-bold">Last Name:</span>
          <span className="ml-2">{userinfo.lastname}</span>
        </div>
        <div className="flex items-center border-b border-white pb-2">
          <FontAwesomeIcon icon={faCheckCircle} className="mr-2 text-yellow-400" />
          <span className="font-bold">Total Problems Solved:</span>
          <span className="ml-2">{userinfo.solvedProblemsId.length}</span>
        </div>
      </div>
    </div>
  );
};

export default UserComponent;
