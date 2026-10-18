import React from 'react';
import { ArrowRight, Trophy } from 'lucide-react';
import { FEATURED_ATHLETES } from '../data/yonexData';

export default function AthleteSpotlight() {
  return (
    <section id="athletes" className="py-14 lg:py-20 bg-gray-50 border-t border-gray-200">
      <div className="max-w-[1920px] mx-auto px-4 sm:px-8">
        {/* Header */}
        <div className="flex items-center justify-between mb-8 pb-3 border-b border-gray-200">
          <div>
            <div className="inline-flex items-center gap-1.5 text-xs font-bold text-yonex-blue uppercase tracking-widest mb-1">
              <Trophy size={14} />
              <span>Team Yonex</span>
            </div>
            <h2 className="font-heading font-black text-3xl sm:text-4xl lg:text-5xl text-yonex-dark tracking-wide uppercase">
              FEATURED ATHLETES
            </h2>
          </div>

          <a 
            href="#all-athletes" 
            className="hidden sm:inline-flex items-center gap-1.5 text-xs font-heading font-bold uppercase tracking-wider text-yonex-dark hover:text-yonex-blue transition-colors border border-gray-300 px-4 py-2 rounded bg-white hover:bg-gray-50"
          >
            <span>VIEW ALL ATHLETES</span>
            <ArrowRight size={14} />
          </a>
        </div>

        {/* 3-Athlete Cards */}
        <div className="grid grid-cols-1 md:grid-cols-3 gap-6 lg:gap-8">
          {FEATURED_ATHLETES.map((ath) => (
            <div 
              key={ath.id}
              className="group bg-white rounded-sm overflow-hidden border border-gray-200 shadow-sm hover:shadow-xl hover:border-yonex-blue transition-all duration-300"
            >
              {/* Image composite */}
              <div className="relative aspect-[4/4.5] overflow-hidden bg-gray-100">
                {/* Action shot background */}
                <img 
                  src={ath.actionImage} 
                  alt={`${ath.name} in action`}
                  className="w-full h-full object-cover object-top opacity-30 group-hover:opacity-40 transition-opacity"
                  loading="lazy"
                />

                {/* Profile Portrait foreground */}
                <div className="absolute inset-0 flex items-center justify-center p-4">
                  <img 
                    src={ath.profileImage} 
                    alt={ath.name}
                    className="h-full w-auto object-contain object-bottom group-hover:scale-105 transition-transform duration-500"
                    loading="lazy"
                  />
                </div>

                <div className="absolute inset-0 bg-gradient-to-t from-black/80 via-transparent to-transparent" />

                {/* Overlaid Athlete info */}
                <div className="absolute bottom-4 left-4 right-4 text-white z-10">
                  <div className="flex items-center gap-2 mb-1">
                    <img 
                      src={ath.flagUrl} 
                      alt={ath.country} 
                      className="w-5 h-3.5 object-cover rounded-sm shadow-sm"
                    />
                    <span className="text-xs font-bold uppercase tracking-wider text-gray-200">
                      {ath.country} | {ath.sport}
                    </span>
                  </div>

                  <h3 className="font-heading font-black text-2xl text-white uppercase tracking-tight">
                    {ath.name}
                  </h3>

                  <div className="mt-1 flex items-center justify-between text-xs text-yonex-green font-semibold">
                    <span>Racquet: {ath.racquet}</span>
                    <span className="text-white group-hover:translate-x-1 transition-transform flex items-center gap-1 font-bold">
                      Profile &rarr;
                    </span>
                  </div>
                </div>
              </div>
            </div>
          ))}
        </div>

        {/* Mobile View All */}
        <div className="mt-8 text-center sm:hidden">
          <a 
            href="#all-athletes" 
            className="inline-flex items-center gap-1.5 text-xs font-heading font-bold uppercase tracking-wider text-yonex-dark border border-gray-300 px-6 py-3 rounded bg-white w-full justify-center"
          >
            <span>VIEW ALL ATHLETES</span>
            <ArrowRight size={14} />
          </a>
        </div>
      </div>
    </section>
  );
}
