import axios from 'axios';
import { BASE_URL } from "../constant"; 



const CategoryService = {
  getAllCategories: async () => {
    try {
      const response = await axios.get(`${BASE_URL}/Algomania/Category/allcategories`);
      return response.data;
    } catch (error) {
      throw new Error('Failed to fetch categories');
    }
  },
};

export default CategoryService;
