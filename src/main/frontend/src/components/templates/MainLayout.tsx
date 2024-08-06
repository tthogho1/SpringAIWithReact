import React, { useEffect } from 'react';
import Header from './Header';
import type { HeaderProps } from './Header';
import Footer from './Footer';

type LayoutProps = HeaderProps & {
  children: React.ReactNode
}

export default function Layout({ children, ...headerProps}:  LayoutProps ) {
  useEffect(() => {
    console.log('Layout rendered');
  }, []);

  return (
    <div>
      <Header {...headerProps}/>
      {children}
      <Footer/>
    </div>
  );
}