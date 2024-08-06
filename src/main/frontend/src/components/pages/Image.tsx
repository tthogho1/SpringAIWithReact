import React, { useState , FormEvent} from 'react';
import { Button } from '../ui/button';
import { Input } from '../ui/input';
import  {Image as Img}  from '../ui/img';

const Image = () =>{
  const [input, setInput] = useState('');
  const [imgUrl, setImgUrl] = useState('');

  const handleSubmit = async (e :FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    try {
      const encodedInput = encodeURIComponent(input);
      const url = `./ImageGeneration?prompt=${encodedInput}`;

      const res = await fetch(url, {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json',
        },
      });
      const data = await res.json();
      setImgUrl(data.imageUrl); 
    } catch (error) {
      console.error('Error:', error);
      alert("Error Occured");
      // setResponse('Error Occured');
    }
    setInput('');
  };

  return (
    <div className="max-w-md mx-auto mt-10 p-6 bg-white rounded-lg shadow-md">
      <form onSubmit={handleSubmit} className="space-y-4">
        <Input
          type="text"
          value={input}
          onChange={(e) => setInput(e.target.value)}
          placeholder="Input your message"
          className="w-full"
        />
        <Button type="submit" className="w-full">Send</Button>
      </form>
      <Img
        src={imgUrl}
        alt="AI's Image"
        style={{ width: '200px', height: '200px' }}
      />
    </div>
  );
}

export default Image;
