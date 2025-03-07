import { useState, useEffect } from 'react';
import axios from 'axios';

const useProblemService = () => {
  const [problems, setProblems] = useState([]);
  const [page, setPage] = useState(0);
  const [loading, setLoading] = useState(false);
  const [hasMore, setHasMore] = useState(true);
  const defaultSize = 2; 

  useEffect(() => {
  
    const fetchData = async () => {
      setLoading(true);
      try {
        const response = await axios.get(`http://localhost:8000/Algomania/problem/page?page=${page}&size=${defaultSize}`);
        const data = response.data;
        setProblems(prevProblems => [...prevProblems, ...data.content]);
        setHasMore(!data.last); 
      } catch (error) {
        console.error('Error fetching data:', error);
      }
      setLoading(false);
    };

    fetchData();
  }, [page]); 
  useEffect(() => {
 
    const handleScroll = () => {
      if (
        window.innerHeight + document.documentElement.scrollTop ===
        document.documentElement.offsetHeight
      ) {
        if (!loading && hasMore) {
          setPage(prevPage => prevPage + 1);
        }
      }
    };

    
    window.addEventListener('scroll', handleScroll);

 
    return () => window.removeEventListener('scroll', handleScroll);
  }, [loading, hasMore]); 

  return { problems, loading, hasMore };
};

export default useProblemService;
