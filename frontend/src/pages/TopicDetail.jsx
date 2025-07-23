import { useParams } from 'react-router-dom';

const TopicDetail = () => {
  const { id } = useParams();

  return (
    <div className="p-4">
      <h2 className="text-xl font-semibold">Topic Details - ID: {id}</h2>
      {/* Here you can fetch and display topic-specific details */}
    </div>
  );
};

export default TopicDetail;
