import React, { useState , FormEvent} from 'react';
import { Button } from '../ui/button';
import { Input } from '../ui/input';
import { Textarea } from '../ui/textarea';

export default function Chat() {
  const [input, setInput] = useState('');
  const [response, setResponse] = useState('');

  const handleSubmit = async (e :FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    try {
      const encodedInput = encodeURIComponent(input);
      const url = `./ai?message=${encodedInput}`;

      const res = await fetch(url, {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json',
        },
      });
      const data = await res.json();
      setResponse(data.completion); 
    } catch (error) {
      console.error('Error:', error);
      setResponse('Error Occured');
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
      <Textarea
        value={response}
        readOnly
        placeholder="AI's response"
        className="mt-4 w-full h-32"
      />
    </div>
  );
}
