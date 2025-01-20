import { PixKeyType } from "@/types/form.types";

export const validatePixKey = (type: PixKeyType, value: string): boolean => {
    switch (type) {
      case 'email':
        return /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(value);
      case 'phone':
        return /^(\(\d{2}\)\s?|\d{2})\s?\d{5}[-\s]?\d{4}$/.test(value);
      case 'document':
        return /^\d{11}$|^\d{14}$/.test(value);
      default:
        return false;
    }
  };