import React, { useState } from 'react';
import { Globe, MapPin, X } from 'lucide-react';

export default function AnnouncementBar() {
  const [visible, setVisible] = useState(true);

  if (!visible) return null;

  return (
    <div className="bg-[#191A1C] text-white text-xs border-b border-gray-800 tracking-wider">
      <div className="max-w-[1920px] mx-auto px-4 sm:px-8 py-2 flex items-center justify-between">
        <div className="hidden lg:flex items-center space-x-6 text-gray-300">
          <a 
            href="#international" 
            className="flex items-center gap-1.5 hover:text-white transition-colors duration-200"
          >
            <Globe size={13} className="text-yonex-blue" />
            <span>International Sites</span>
          </a>
          <span className="text-gray-600">|</span>
          <a 
            href="#locator" 
            className="flex items-center gap-1.5 hover:text-white transition-colors duration-200"
          >
            <MapPin size={13} className="text-yonex-green" />
            <span>Find A Store</span>
          </a>
        </div>

        <div className="flex-1 text-center font-medium">
          <span className="text-white font-bold uppercase tracking-widest text-[11px] sm:text-xs">
            Free Shipping
          </span>
          <span className="text-gray-300 text-[11px] sm:text-xs ml-1.5">
            : on all US orders over $99
          </span>
        </div>

        <div className="flex items-center">
          <button 
            onClick={() => setVisible(false)}
            className="text-gray-400 hover:text-white p-0.5 rounded transition-colors"
            title="Dismiss announcement"
          >
            <X size={14} />
          </button>
        </div>
      </div>
    </div>
  );
}
