import React from 'react';
import { CheckCircle2 } from 'lucide-react';
import { useCart } from '../context/CartContext';

export default function Toast() {
  const { toastMessage } = useCart();

  if (!toastMessage) return null;

  return (
    <div className="fixed bottom-6 right-6 z-50 animate-fade-in">
      <div className="bg-[#191A1C] text-white px-5 py-3.5 rounded shadow-2xl flex items-center gap-3 border border-gray-700">
        <CheckCircle2 size={18} className="text-yonex-green shrink-0" />
        <span className="text-xs sm:text-sm font-semibold">{toastMessage}</span>
      </div>
    </div>
  );
}
