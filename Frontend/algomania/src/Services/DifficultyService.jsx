import axios from 'axios';

const BASE_URL = 'http://localhost:8080/Algomania';

const DifficultyService = {
  getAllDifficulty: async () => {
    try {
      const response = await axios.get(`${BASE_URL}/Difficulty/alldifficulty`);
      return response.data;
    } catch (error) {
      throw new Error('Failed to fetch difficulty levels');
    }
  },
};

export default DifficultyService;
