import React, { useState, useRef } from 'react';
import { Play, Pause, Volume2, VolumeX, ArrowRight } from 'lucide-react';

export default function HeroBanner() {
  const [isPlaying, setIsPlaying] = useState(true);
  const [isMuted, setIsMuted] = useState(true);
  const videoRef = useRef(null);

  const togglePlay = () => {
    if (!videoRef.current) return;
    if (isPlaying) {
      videoRef.current.pause();
      setIsPlaying(false);
    } else {
      videoRef.current.play();
      setIsPlaying(true);
    }
  };

  const toggleMute = () => {
    if (!videoRef.current) return;
    videoRef.current.muted = !isMuted;
    setIsMuted(!isMuted);
  };

  return (
    <section className="relative w-full overflow-hidden bg-black aspect-[4/3] sm:aspect-[16/9] lg:aspect-[2.2/1] min-h-[520px] max-h-[820px] flex items-end">
      {/* Background Video */}
      <div className="absolute inset-0 w-full h-full">
        <video
          ref={videoRef}
          playsInline
          autoPlay
          loop
          muted={isMuted}
          className="w-full h-full object-cover object-center"
          poster="https://us.yonex.com/cdn/shop/files/TOKITO_ODA.jpg?v=1784579463&width=1800"
        >
          <source 
            src="https://us.yonex.com/cdn/shop/videos/c/vp/0ae6dc56c84a4baa8e70308efc6c4325/0ae6dc56c84a4baa8e70308efc6c4325.HD-1080p-7.2Mbps-96724142.mp4?v=0" 
            type="video/mp4" 
          />
        </video>

        {/* Gradient Overlay for high text contrast */}
        <div className="absolute inset-0 bg-gradient-to-t from-black/85 via-black/35 to-black/20" />
      </div>

      {/* Floating Video Controls */}
      <div className="absolute top-6 right-6 sm:top-8 sm:right-8 z-20 flex items-center gap-2 bg-black/40 backdrop-blur-md p-1.5 rounded-full border border-white/20">
        <button
          onClick={togglePlay}
          className="p-2 text-white hover:text-yonex-blue transition-colors rounded-full hover:bg-white/10"
          aria-label={isPlaying ? "Pause video" : "Play video"}
          title={isPlaying ? "Pause" : "Play"}
        >
          {isPlaying ? <Pause size={16} /> : <Play size={16} />}
        </button>
        <button
          onClick={toggleMute}
          className="p-2 text-white hover:text-yonex-blue transition-colors rounded-full hover:bg-white/10"
          aria-label={isMuted ? "Unmute video" : "Mute video"}
          title={isMuted ? "Unmute" : "Mute"}
        >
          {isMuted ? <VolumeX size={16} /> : <Volume2 size={16} />}
        </button>
      </div>

      {/* Hero Content */}
      <div className="relative z-10 max-w-[1920px] mx-auto w-full px-6 sm:px-12 pb-12 sm:pb-16 lg:pb-20">
        <div className="max-w-2xl text-white">
          <div className="inline-flex items-center gap-2 bg-yonex-blue/90 text-white px-3 py-1 text-xs font-bold uppercase tracking-widest rounded-sm mb-3">
            <span className="w-1.5 h-1.5 rounded-full bg-white animate-pulse"></span>
            New Generation
          </div>
          
          <h1 className="font-heading font-black text-4xl sm:text-5xl lg:text-7xl tracking-tight leading-[0.95] text-white uppercase drop-shadow-md">
            POWER EVERY ATTACK. <br />
            <span className="text-transparent bg-clip-text bg-gradient-to-r from-white via-gray-200 to-gray-400">
              EZONE 100 & 98
            </span>
          </h1>

          <p className="mt-4 text-sm sm:text-base text-gray-200 font-medium leading-relaxed max-w-xl drop-shadow">
            Engineered in Niigata, Japan with 2G-Namd™ Speed graphite and ISOMETRIC™ geometry for maximum power, plush impact sensation, and relentless precision.
          </p>

          <div className="mt-8 flex flex-wrap items-center gap-4">
            <a 
              href="#products" 
              className="bg-yonex-blue text-white px-8 py-4 font-heading font-bold text-sm tracking-widest uppercase rounded hover:bg-yonex-darkBlue transition-all transform hover:-translate-y-0.5 shadow-lg flex items-center gap-2"
            >
              <span>SHOP EZONE</span>
              <ArrowRight size={16} />
            </a>

            <a 
              href="#technology" 
              className="bg-white/10 backdrop-blur-md text-white border border-white/40 px-8 py-4 font-heading font-bold text-sm tracking-widest uppercase rounded hover:bg-white hover:text-yonex-dark transition-all transform hover:-translate-y-0.5"
            >
              EXPLORE RACQUETS
            </a>
          </div>
        </div>
      </div>
    </section>
  );
}
