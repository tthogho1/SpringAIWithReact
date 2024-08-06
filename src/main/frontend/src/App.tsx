import React, { useState } from 'react';
import Layout from './components/templates/MainLayout';
import Chat from './components/pages/Chat';
import Image from './components/pages/Image';

const App = () => {
  const [status, setStatus] = useState<'chat' | 'image'>('chat');

  const  onChatClick = () => {
    setStatus('chat');
  } ;
  const  onImageClick = () => {
    setStatus('image');
  };


  const Components = () => {
    return (
      status == "chat" ? <Chat /> : <Image/>
    );
  }

  return (
      <Layout onChatClick={onChatClick} onImageClick={onImageClick}>
        <Components />
      </Layout>
  );
};

export default App;