import React, { useState, useEffect } from 'react';
import { Search, User, ShoppingBag, Menu } from 'lucide-react';
import YonexLogo from './YonexLogo';
import MegaMenu from './MegaMenu';
import MobileDrawer from './MobileDrawer';
import { useCart } from '../context/CartContext';

export default function Header() {
  const [activeMenu, setActiveMenu] = useState(null);
  const [isMobileOpen, setIsMobileOpen] = useState(false);
  const [isScrolled, setIsScrolled] = useState(false);
  const { totalCount, setIsCartOpen, setIsSearchOpen } = useCart();

  useEffect(() => {
    const handleScroll = () => {
      setIsScrolled(window.scrollY > 20);
    };
    window.addEventListener('scroll', handleScroll);
    return () => window.removeEventListener('scroll', handleScroll);
  }, []);

  const navItems = [
    { id: 'racquets', label: 'RACQUETS' },
    { id: 'sports', label: 'SPORTS' },
    { id: 'men', label: 'MEN' },
    { id: 'women', label: 'WOMEN' },
    { id: 'athletes', label: 'ATHLETE INSPIRED' },
    { id: 'about', label: 'MADE BY YONEX' }
  ];

  return (
    <header className={`sticky top-0 z-40 bg-white transition-all duration-200 ${
      isScrolled ? 'shadow-md border-b border-gray-200' : 'border-b border-gray-100'
    }`}>
      <div className="max-w-[1920px] mx-auto px-4 sm:px-8 h-16 lg:h-20 flex items-center justify-between relative">
        {/* Left: Mobile hamburger & Logo */}
        <div className="flex items-center gap-3">
          <button 
            onClick={() => setIsMobileOpen(true)}
            className="lg:hidden p-2 text-yonex-dark hover:text-yonex-blue transition-colors focus:outline-none"
            aria-label="Open mobile menu"
          >
            <Menu size={24} />
          </button>
          
          <a href="/" className="flex items-center">
            <YonexLogo className="h-7 sm:h-8 lg:h-9" />
          </a>
        </div>

        {/* Center: Desktop Navigation Bar */}
        <nav className="hidden lg:flex items-center space-x-1 h-full">
          {navItems.map((item) => (
            <div 
              key={item.id}
              className="h-full flex items-center"
              onMouseEnter={() => setActiveMenu(item.id)}
            >
              <button 
                className={`px-3.5 py-2 font-heading font-bold text-sm tracking-widest text-[#191A1C] transition-all relative ${
                  activeMenu === item.id 
                    ? 'text-yonex-blue' 
                    : 'hover:text-yonex-blue'
                }`}
              >
                {item.label}
                {/* Active indicator bar */}
                <span className={`absolute bottom-0 left-0 w-full h-1 bg-yonex-blue transition-transform duration-200 ${
                  activeMenu === item.id ? 'scale-x-100' : 'scale-x-0'
                }`} />
              </button>
            </div>
          ))}
        </nav>

        {/* Right: Actions (Search, Account, Cart) */}
        <div className="flex items-center space-x-2 sm:space-x-4">
          <button 
            onClick={() => setIsSearchOpen(true)}
            className="p-2 text-yonex-dark hover:text-yonex-blue transition-colors rounded-full hover:bg-gray-100"
            title="Search products"
          >
            <Search size={20} />
          </button>

          <a 
            href="#account" 
            className="hidden sm:flex p-2 text-yonex-dark hover:text-yonex-blue transition-colors rounded-full hover:bg-gray-100"
            title="Account"
          >
            <User size={20} />
          </a>

          <button 
            onClick={() => setIsCartOpen(true)}
            className="p-2 text-yonex-dark hover:text-yonex-blue transition-colors relative rounded-full hover:bg-gray-100"
            title="View Cart"
          >
            <ShoppingBag size={21} />
            {totalCount > 0 && (
              <span className="absolute -top-1 -right-1 bg-yonex-blue text-white font-bold text-[10px] w-5 h-5 rounded-full flex items-center justify-center animate-fade-in shadow-sm">
                {totalCount}
              </span>
            )}
          </button>
        </div>

        {/* Mega Menu Dropdown */}
        <MegaMenu 
          activeMenu={activeMenu} 
          onClose={() => setActiveMenu(null)} 
        />
      </div>

      {/* Mobile Drawer */}
      <MobileDrawer 
        isOpen={isMobileOpen}
        onClose={() => setIsMobileOpen(false)}
        onOpenCart={() => setIsCartOpen(true)}
        cartCount={totalCount}
      />
    </header>
  );
}
