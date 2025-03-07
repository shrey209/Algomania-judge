import axios from 'axios';

const BASE_URL = 'http://localhost:8000/Algomania';

const CategoryService = {
  getAllCategories: async () => {
    try {
      const response = await axios.get(`${BASE_URL}/Category/allcategories`);
      return response.data;
    } catch (error) {
      throw new Error('Failed to fetch categories');
    }
  },
};

export default CategoryService;
