import React, { useEffect, useState } from 'react';
import DifficultyService from '../Services/DifficultyService';
import CategoryService from '../Services/Categoryservice';
import ProblemCard from './ProblemCard';
import axios from 'axios';

import { useAuth } from '../context/context';

import { BASE_URL } from '../constant';

const newUrl = BASE_URL+'/Algomania/problem/filter';

const DisplayProblem = ({ selectedCategoryIds, selectedDifficultyIds, solved }) => {
  const [difficulties, setDifficulties] = useState({});
  const [categories, setCategories] = useState({});
  const [problems, setProblems] = useState([]);
  const [loading, setLoading] = useState(true);
  //const [solvedProblems, setSolvedProblems] = useState([]);
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

  // useEffect(() => {
  //   if (solved) {
  //     setSolvedProblems(userinformation.solvedProblemsId || []);
  //   } else {
  //     setSolvedProblems([]);
  //   }
  // }, [solved, userinformation]);

  useEffect(() => {
    const fetchProblems = async () => {
      setLoading(true);
      console.log(selectedDifficultyIds)
      console.log(selectedCategoryIds)
      console.log(solved)

      try {
        const response = await axios.post(newUrl, {
          difficultyIds: selectedDifficultyIds,
          categoryIds: selectedCategoryIds,
          solved: solved,
          page: 1
        },
        {
          withCredentials: true 
        } 
      );

        setProblems(response.data.content);
      } catch (error) {
        console.error('Failed to fetch problems', error);
      } finally {
        setLoading(false);
      }
    };

    fetchProblems();
  }, [selectedCategoryIds, selectedDifficultyIds,solved]);

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
