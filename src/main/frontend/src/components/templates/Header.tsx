import React from 'react';

type HeaderProps = {
  onChatClick?: () => void;
  onImageClick?: () => void;
}

const Header: React.FC<HeaderProps> = ({ onChatClick, onImageClick }) => {
  const navStyles = {
    ul: {
      listStyleType: 'none',
      padding: 0,
      margin: 0,
      display: 'flex',
    },
    li: {
      marginRight: '20px',
    },
    a: {
      textDecoration: 'none',
      color: 'black',
      cursor: 'pointer',
    },
    header: {
      padding: '10px',
      background: 'gray',
      color: 'white',
      fontWeight:'bold'
    }
  };

  return (
    <header style={navStyles.header}>
      <nav>
        <ul style={navStyles.ul}>
          <li style={navStyles.li}>
            <a onClick={onChatClick} style={navStyles.a}>
              Chat
            </a>
          </li>
          <li style={navStyles.li}>
            <a onClick={onImageClick} style={navStyles.a}>
              Image
            </a>
          </li>
        </ul>
      </nav>
    </header>
  );
};

export default Header;
export type { HeaderProps }