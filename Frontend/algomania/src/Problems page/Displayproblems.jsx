import React, { useEffect, useState } from 'react';
import DifficultyService from '../Services/DifficultyService';
import CategoryService from '../Services/Categoryservice';
import ProblemCard from './ProblemCard';
import axios from 'axios';
import InfiniteScroll from 'react-infinite-scroll-component';
import { useAuth } from '../context/context';

const BASE_URL = 'http://localhost:8000/Algomania/problem/filter';

const DisplayProblem = ({ selectedCategoryIds, selectedDifficultyIds, solved }) => {
  const [difficulties, setDifficulties] = useState({});
  const [categories, setCategories] = useState({});
  const [problems, setProblems] = useState([]);
  const [loading, setLoading] = useState(true);
  const [solvedProblems, setSolvedProblems] = useState([]);
  const { userinformation } = useAuth();
  
  useEffect(() => {
    const fetchData = async () => {
      try {
        const [difficultiesData, categoriesData] = await Promise.all([
          DifficultyService.getAllDifficulty(),
          CategoryService.getAllCategories()
        ]);

        setDifficulties(difficultiesData.reduce((obj, item) => {
          obj[item.id] = item;
          return obj;
        }, {}));

        setCategories(categoriesData.reduce((obj, item) => {
          obj[item.id] = item;
          return obj;
        }, {}));
      } catch (error) {
        console.error('Failed to fetch data', error);
      }
    };

    fetchData();
  }, []);

  useEffect(() => {
    if (solved) {
      setSolvedProblems(userinformation.solvedProblemsId || []);
    } else {
      setSolvedProblems([]);
    }
  }, [solved, userinformation]);

  useEffect(() => {
    const fetchProblems = async () => {
      setLoading(true);

      try {
        const response = await axios.post(BASE_URL, {
          difficultyIds: selectedDifficultyIds,
          categoryIds: selectedCategoryIds,
          excludedIds: solvedProblems,
          page: 1
        });

        setProblems(response.data.content);
      } catch (error) {
        console.error('Failed to fetch problems', error);
      } finally {
        setLoading(false);
      }
    };

    fetchProblems();
  }, [selectedCategoryIds, selectedDifficultyIds]);

  return (
    <div className="space-y-4">
      {loading ? (
        <div>Loading...</div>
      ) : (
        problems.map(problem => {
          const difficulty = difficulties[problem.difficultyId];
          const category = categories[problem.categoryId];

          return (
            <ProblemCard
              key={problem.id}
              id={problem.id}
              difficulty={difficulty ? difficulty.level : 'Unknown'}
              category={category ? category.type : 'Unknown'}
              title={problem.title}
              problemDetailsId={problem.problemDetailsId}
            />
          );
        })
      )}
    </div>
  );
};

export default DisplayProblem;
