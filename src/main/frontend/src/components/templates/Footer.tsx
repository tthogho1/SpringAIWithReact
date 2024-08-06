import React from 'react';

const Footer: React.FC = () => {
  return (
    <footer>
      <p>&copy; {new Date().getFullYear()} Your Company Name</p>
    </footer>
  );
};

export default Footer;