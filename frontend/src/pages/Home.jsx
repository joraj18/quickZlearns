import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';

const Home = () => {
  const [topics, setTopics] = useState([]);
  const navigate = useNavigate();

  useEffect(() => {
    const fetchTopics = async () => {
      try {
        const response = await fetch('http://localhost:8080/topics');
        const data = await response.json();
        setTopics(data);
      } catch (error) {
        console.error('Error fetching topics:', error);
      }
    };

    fetchTopics();
  }, []);

  return (
    <div className="p-4 flex flex-col items-center">
      <h1 className="text-2xl font-bold mb-6">Topics</h1>
      <div className="flex flex-col gap-4 w-full max-w-sm">
        {topics.map((topic) => (
          <button
            key={topic.topicId}
            onClick={() => navigate(`/topic/${topic.topicId}`)}
            className="bg-blue-500 text-white px-4 py-2 rounded-lg hover:bg-blue-600 transition text-center"
          >
            {topic.topicTitle}
          </button>
        ))}
      </div>
    </div>
  );
};

export default Home;

