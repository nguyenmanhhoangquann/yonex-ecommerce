import React, { useState } from 'react';
import { X, ChevronDown, ChevronRight, Globe, MapPin, User, ShoppingBag } from 'lucide-react';
import YonexLogo from './YonexLogo';

export default function MobileDrawer({ isOpen, onClose, onOpenCart, cartCount }) {
  const [openSection, setOpenSection] = useState(null);

  if (!isOpen) return null;

  const toggleSection = (section) => {
    setOpenSection(openSection === section ? null : section);
  };

  return (
    <div className="fixed inset-0 z-50 lg:hidden">
      {/* Backdrop */}
      <div 
        className="fixed inset-0 bg-black/60 backdrop-blur-sm transition-opacity"
        onClick={onClose}
      />

      {/* Drawer */}
      <div className="fixed inset-y-0 left-0 w-[85%] max-w-sm bg-white shadow-2xl z-50 flex flex-col justify-between overflow-y-auto animate-fade-in">
        <div>
          {/* Top header inside drawer */}
          <div className="p-4 border-b border-gray-100 flex items-center justify-between">
            <YonexLogo className="h-7" />
            <button 
              onClick={onClose}
              className="p-1.5 text-gray-700 hover:text-black rounded hover:bg-gray-100 transition-colors"
            >
              <X size={20} />
            </button>
          </div>

          {/* Navigation Links */}
          <nav className="p-4 space-y-1">
            {/* Racquets Accordion */}
            <div className="border-b border-gray-100 py-2">
              <button 
                onClick={() => toggleSection('racquets')}
                className="w-full flex items-center justify-between text-base font-heading font-bold tracking-wider text-yonex-dark py-1"
              >
                <span>RACQUETS</span>
                <ChevronDown size={18} className={`transition-transform duration-200 ${openSection === 'racquets' ? 'rotate-180 text-yonex-blue' : ''}`} />
              </button>
              {openSection === 'racquets' && (
                <div className="pl-3 pt-2 pb-1 space-y-2 text-sm text-gray-600 font-medium animate-fade-in">
                  <div className="font-bold text-xs uppercase text-yonex-blue pt-1">Tennis Racquets</div>
                  <a href="#ezone" onClick={onClose} className="block py-1 hover:text-yonex-blue">EZONE Series (Power)</a>
                  <a href="#vcore" onClick={onClose} className="block py-1 hover:text-yonex-blue">VCORE Series (Spin)</a>
                  <a href="#percept" onClick={onClose} className="block py-1 hover:text-yonex-blue">PERCEPT Series (Control)</a>
                  
                  <div className="font-bold text-xs uppercase text-yonex-green pt-2">Badminton Racquets</div>
                  <a href="#astrox" onClick={onClose} className="block py-1 hover:text-yonex-green">ASTROX Series (Power)</a>
                  <a href="#nanoflare" onClick={onClose} className="block py-1 hover:text-yonex-green">NANOFLARE Series (Speed)</a>
                  <a href="#arcsaber" onClick={onClose} className="block py-1 hover:text-yonex-green">ARCSABER Series (Precision)</a>
                </div>
              )}
            </div>

            {/* Sports Accordion */}
            <div className="border-b border-gray-100 py-2">
              <button 
                onClick={() => toggleSection('sports')}
                className="w-full flex items-center justify-between text-base font-heading font-bold tracking-wider text-yonex-dark py-1"
              >
                <span>SPORTS</span>
                <ChevronDown size={18} className={`transition-transform duration-200 ${openSection === 'sports' ? 'rotate-180 text-yonex-blue' : ''}`} />
              </button>
              {openSection === 'sports' && (
                <div className="pl-3 pt-2 pb-1 space-y-2 text-sm text-gray-600 font-medium animate-fade-in">
                  <a href="#tennis" onClick={onClose} className="block py-1 hover:text-yonex-blue">Tennis</a>
                  <a href="#badminton" onClick={onClose} className="block py-1 hover:text-yonex-blue">Badminton</a>
                  <a href="#golf" onClick={onClose} className="block py-1 hover:text-yonex-blue">Golf</a>
                  <a href="#pickleball" onClick={onClose} className="block py-1 hover:text-yonex-blue">Pickleball</a>
                  <a href="#snowboarding" onClick={onClose} className="block py-1 hover:text-yonex-blue">Snowboarding</a>
                </div>
              )}
            </div>

            {/* Men */}
            <div className="border-b border-gray-100 py-2">
              <a 
                href="#men" 
                onClick={onClose}
                className="block text-base font-heading font-bold tracking-wider text-yonex-dark py-1 hover:text-yonex-blue"
              >
                MEN
              </a>
            </div>

            {/* Women */}
            <div className="border-b border-gray-100 py-2">
              <a 
                href="#women" 
                onClick={onClose}
                className="block text-base font-heading font-bold tracking-wider text-yonex-dark py-1 hover:text-yonex-blue"
              >
                WOMEN
              </a>
            </div>

            {/* Juniors */}
            <div className="border-b border-gray-100 py-2">
              <a 
                href="#juniors" 
                onClick={onClose}
                className="block text-base font-heading font-bold tracking-wider text-yonex-dark py-1 hover:text-yonex-blue"
              >
                JUNIORS
              </a>
            </div>

            {/* Athlete Inspired */}
            <div className="border-b border-gray-100 py-2">
              <a 
                href="#athletes" 
                onClick={onClose}
                className="block text-base font-heading font-bold tracking-wider text-yonex-dark py-1 hover:text-yonex-blue"
              >
                ATHLETE INSPIRED
              </a>
            </div>

            {/* Made By Yonex */}
            <div className="border-b border-gray-100 py-2">
              <a 
                href="#made-by-yonex" 
                onClick={onClose}
                className="block text-base font-heading font-bold tracking-wider text-yonex-dark py-1 hover:text-yonex-blue"
              >
                MADE BY YONEX
              </a>
            </div>
          </nav>
        </div>

        {/* Footer inside mobile drawer */}
        <div className="p-4 bg-gray-50 border-t border-gray-100 space-y-3">
          <div className="flex items-center justify-between text-xs font-semibold text-gray-700">
            <a href="#account" className="flex items-center gap-1.5 hover:text-yonex-blue">
              <User size={15} />
              <span>My Account</span>
            </a>
            <button 
              onClick={() => { onClose(); onOpenCart(); }}
              className="flex items-center gap-1.5 text-yonex-blue font-bold"
            >
              <ShoppingBag size={15} />
              <span>Cart ({cartCount})</span>
            </button>
          </div>
          <div className="flex items-center justify-between text-xs text-gray-500 pt-2 border-t border-gray-200">
            <span className="flex items-center gap-1">
              <Globe size={13} />
              United States ($ USD)
            </span>
            <a href="#locator" className="flex items-center gap-1 hover:underline">
              <MapPin size={13} />
              Find Store
            </a>
          </div>
        </div>
      </div>
    </div>
  );
}
