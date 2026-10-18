import React, { useState } from 'react';
import { ArrowRight, ChevronRight } from 'lucide-react';
import { SHOP_BY_SPORT } from '../data/yonexData';

export default function ShopBySport() {
  const [activeSportIndex, setActiveSportIndex] = useState(0);
  const activeSport = SHOP_BY_SPORT[activeSportIndex];

  return (
    <section className="py-14 lg:py-20 bg-gray-50 border-t border-gray-200">
      <div className="max-w-[1920px] mx-auto px-4 sm:px-8">
        <div className="mb-8">
          <span className="text-xs font-bold text-yonex-blue uppercase tracking-widest block mb-1">
            Choose Your Arena
          </span>
          <h2 className="font-heading font-black text-3xl sm:text-4xl lg:text-5xl text-yonex-dark tracking-wide uppercase">
            SHOP BY SPORT
          </h2>
        </div>

        <div className="grid grid-cols-1 lg:grid-cols-12 gap-8 items-center">
          {/* Left: Sports list menu */}
          <div className="lg:col-span-5 flex flex-col space-y-1 sm:space-y-2">
            {SHOP_BY_SPORT.map((sport, index) => {
              const isActive = index === activeSportIndex;
              return (
                <button
                  key={sport.id}
                  onClick={() => setActiveSportIndex(index)}
                  onMouseEnter={() => setActiveSportIndex(index)}
                  className={`text-left px-5 py-4 rounded transition-all duration-300 flex items-center justify-between group ${
                    isActive 
                      ? 'bg-white shadow-md border-l-4 border-yonex-blue text-yonex-dark translate-x-2' 
                      : 'hover:bg-white/60 text-gray-500 hover:text-yonex-dark'
                  }`}
                >
                  <div>
                    <h4 className={`font-heading font-bold text-xl sm:text-2xl tracking-wide uppercase transition-colors ${
                      isActive ? 'text-yonex-blue' : 'text-gray-700 group-hover:text-black'
                    }`}>
                      {sport.label}
                    </h4>
                    {isActive && (
                      <p className="text-xs text-gray-500 mt-1 animate-fade-in font-medium">
                        {sport.subtitle}
                      </p>
                    )}
                  </div>

                  <ChevronRight 
                    size={20} 
                    className={`transition-all duration-200 ${
                      isActive ? 'text-yonex-blue translate-x-1' : 'text-gray-300 group-hover:text-gray-600'
                    }`} 
                  />
                </button>
              );
            })}
          </div>

          {/* Right: Dynamic Athlete & Sport Visual */}
          <div className="lg:col-span-7">
            <div className="relative rounded-sm overflow-hidden aspect-[16/10] sm:aspect-[16/9] bg-black shadow-xl group">
              <img 
                key={activeSport.id}
                src={activeSport.image} 
                alt={activeSport.label} 
                className="w-full h-full object-cover object-center animate-fade-in transition-transform duration-700 group-hover:scale-105"
              />

              {/* Gradient */}
              <div className="absolute inset-0 bg-gradient-to-t from-black/85 via-black/30 to-transparent" />

              {/* Overlay card */}
              <div className="absolute bottom-6 left-6 right-6 sm:bottom-8 sm:left-8 sm:right-8 z-10 flex flex-col sm:flex-row items-start sm:items-end justify-between gap-4 text-white">
                <div>
                  <span className="text-[11px] font-bold uppercase tracking-widest text-yonex-green bg-black/60 backdrop-blur-sm px-2.5 py-1 rounded inline-block mb-1.5">
                    Featured Athlete / Ambassador
                  </span>
                  <h3 className="font-heading font-black text-2xl sm:text-3xl text-white uppercase tracking-tight">
                    {activeSport.athlete}
                  </h3>
                  <p className="text-xs sm:text-sm text-gray-300 mt-1 max-w-md">
                    {activeSport.subtitle}
                  </p>
                </div>

                <a 
                  href={`#${activeSport.id}`}
                  className="bg-yonex-blue text-white px-6 py-3 font-heading font-bold text-xs tracking-widest uppercase rounded hover:bg-yonex-darkBlue transition-all shrink-0 flex items-center gap-1.5 shadow-md"
                >
                  <span>{activeSport.cta}</span>
                  <ArrowRight size={14} />
                </a>
              </div>
            </div>
          </div>
        </div>
      </div>
    </section>
  );
}
