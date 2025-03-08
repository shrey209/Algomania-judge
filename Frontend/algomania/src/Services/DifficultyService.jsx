import axios from 'axios';
import { BASE_URL } from "../constant"; 



const DifficultyService = {
  getAllDifficulty: async () => {
    try {
      const response = await axios.get(`${BASE_URL}/Algomania/Difficulty/alldifficulty`);
      return response.data;
    } catch (error) {
      throw new Error('Failed to fetch difficulty levels');
    }
  },
};

export default DifficultyService;
