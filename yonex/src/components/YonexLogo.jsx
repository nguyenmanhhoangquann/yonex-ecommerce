import React from 'react';

export default function YonexLogo({ className = "h-8", light = false }) {
  return (
    <div className={`flex items-center gap-2 select-none ${className}`}>
      {/* Official Yonex Twin Triangles / Circles Emblem */}
      <svg 
        viewBox="0 0 100 85" 
        className="h-full w-auto aspect-[100/85] shrink-0" 
        fill="none" 
        xmlns="http://www.w3.org/2000/svg"
      >
        {/* Left Blue Symbol */}
        <path 
          d="M32 6C17.6 6 6 17.6 6 32C6 46.4 17.6 58 32 58C46.4 58 58 46.4 58 32C58 17.6 46.4 6 32 6Z" 
          fill="#006CB7" 
        />
        <path 
          d="M26 18L44 32L26 46V18Z" 
          fill="#FFFFFF" 
        />

        {/* Right Green Symbol */}
        <path 
          d="M68 26C53.6 26 42 37.6 42 52C42 66.4 53.6 78 68 78C82.4 78 94 66.4 94 52C94 37.6 82.4 26 68 26Z" 
          fill="#008840" 
        />
        <path 
          d="M62 38L80 52L62 66V38Z" 
          fill="#FFFFFF" 
        />
      </svg>

      {/* YONEX Wordmark */}
      <span 
        className={`font-heading font-black tracking-tighter italic text-2xl lg:text-3xl leading-none transition-colors ${
          light ? 'text-white' : 'text-[#191A1C]'
        }`}
        style={{ letterSpacing: '0.02em', fontStyle: 'italic', fontWeight: 800 }}
      >
        YONEX
      </span>
    </div>
  );
}
