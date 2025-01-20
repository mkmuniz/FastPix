import React from 'react';
import { InputBaseProps } from '../../types/form.types';
export default function InputBase({ label, required = false, children }: InputBaseProps) {
  return (
    <div>
      <label className="block text-sm font-medium text-gray-300">
        {label} {required && '*'}
      </label>
      {children}
    </div>
  );
}