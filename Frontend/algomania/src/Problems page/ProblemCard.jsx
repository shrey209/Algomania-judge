import { useNavigate } from "react-router-dom";
import { ArrowRight, Hash, Folder } from "lucide-react";

const ProblemCard = ({ id, problemDetailsId, difficulty, category, title }) => {
  const navigate = useNavigate();

  const handleSolveClick = () => {
    navigate(`/problemDetails/${problemDetailsId}`, {
      state: { problemDetailsId, difficulty, category, title },
    });
  };

  const getDifficultyTextColor = (level) => {
    switch (level) {
      case "Easy":
        return "text-green-600";
      case "Medium":
        return "text-yellow-600";
      case "Hard":
        return "text-red-600";
      default:
        return "text-gray-600";
    }
  };

  const getDifficultyBgColor = (level) => {
    switch (level) {
      case "Easy":
        return "bg-green-50";
      case "Medium":
        return "bg-yellow-50";
      case "Hard":
        return "bg-red-50";
      default:
        return "bg-white";
    }
  };

  return (
    <div
      className={`group relative overflow-hidden rounded-xl p-6 shadow-lg transition-all duration-300 hover:shadow-xl ${getDifficultyBgColor(
        difficulty
      )}`}
    >
      {/* Purple accent decoration */}
      <div className="absolute -right-4 -top-4 h-24 w-24 rotate-45 bg-purple-600/10"></div>

      <div className="flex items-start justify-between">
        <div className="flex-1">
          {/* Header section */}
          <div className="mb-4 flex items-center gap-4">
            <span className="flex h-10 w-10 items-center justify-center rounded-lg bg-purple-100 text-lg font-bold text-purple-700">
              {id}
            </span>
            <div className="flex flex-wrap gap-2">
              <span
                className={`rounded-full bg-gray-100 px-3 py-1 text-sm font-semibold ${getDifficultyTextColor(
                  difficulty
                )}`}
              >
                {difficulty}
              </span>
              <span className="rounded-full border border-purple-200 bg-purple-100 px-3 py-1 text-sm font-semibold text-purple-700">
                {category}
              </span>
            </div>
          </div>

          {/* Title and metadata */}
          <h3 className="mb-4 text-xl font-bold text-gray-800 transition-colors group-hover:text-purple-700">
            {title}
          </h3>

          <div className="flex items-center gap-6 text-sm text-gray-600">
            <div className="flex items-center gap-2">
              <Hash size={16} className="text-purple-600" />
              <span>Problem {id}</span>
            </div>
            <div className="flex items-center gap-2">
              <Folder size={16} className="text-purple-600" />
              <span>{category}</span>
            </div>
          </div>
        </div>

        {/* Solve button */}
        <button
          onClick={handleSolveClick}
          className="flex items-center gap-2 rounded-full bg-purple-600 px-6 py-2 font-semibold text-white transition-all duration-300 hover:bg-purple-700 hover:shadow-lg hover:shadow-purple-200"
        >
          Solve
          <ArrowRight size={16} />
        </button>
      </div>
    </div>
  );
};

export default ProblemCard;
