import axios from 'axios';
import { BASE_URL } from "../constant"; 


const Base_URL = BASE_URL+'/Algomania/ProblemDetails'; 

const getProblemDetailsById = async (id) => {
  // Parse the id to an integer
  const parsedId = parseInt(id, 10); // Parse to base 10

  try {
    const response = await axios.get(`${Base_URL}/${parsedId}`);
    return response.data;
  } catch (error) {
    if (error.response && error.response.status === 404) {
      throw new Error('Problem details not found');
    } else {
      throw new Error('An error occurred while fetching problem details');
    }
  }
};

export default getProblemDetailsById;
