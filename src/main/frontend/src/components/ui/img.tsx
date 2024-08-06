import React from 'react';

// Propsの型定義
type ImageProps = {
  src: string;
  alt?: string;
  style?: React.CSSProperties;
};

const Image: React.FC<ImageProps> = ({ src, alt = '', style }) => {
  if (!src) return null;
  return <img src={src} alt={alt} style={style} />;
};

export {Image};