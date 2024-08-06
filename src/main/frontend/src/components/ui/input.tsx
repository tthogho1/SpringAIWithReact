import React, { InputHTMLAttributes } from 'react';

interface InputProps extends InputHTMLAttributes<HTMLInputElement> {
  label?: string;
  error?: string;
}

export const Input: React.FC<InputProps> = ({
  label,
  error,
  className = '',
  ...props
}) => {
  const inputClasses = `
    px-3 py-2 
    border rounded-md 
    focus:outline-none focus:ring-2 focus:ring-blue-500 
    ${error ? 'border-red-500' : 'border-gray-300'}
    ${className}
  `.trim();

  return (
    <div className="mb-4">
      {label && (
        <label className="block mb-2 text-sm font-bold text-gray-700">
          {label}
        </label>
      )}
      <input className={inputClasses} {...props} />
      {error && (
        <p className="mt-1 text-xs text-red-500">{error}</p>
      )}
    </div>
  );
};