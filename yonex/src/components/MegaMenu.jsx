import React from 'react';
import { ChevronRight, ArrowRight, Zap, Target, Shield, Award } from 'lucide-react';

export default function MegaMenu({ activeMenu, onClose }) {
  if (!activeMenu) return null;

  return (
    <div 
      className="absolute top-full left-0 w-full bg-white border-b border-gray-200 shadow-2xl z-50 animate-fade-in"
      onMouseLeave={onClose}
    >
      <div className="max-w-[1920px] mx-auto px-6 sm:px-12 py-8">
        {activeMenu === 'racquets' && (
          <div className="grid grid-cols-12 gap-8">
            {/* Column 1: Tennis */}
            <div className="col-span-3 border-r border-gray-100 pr-6">
              <div className="flex items-center gap-2 mb-4 pb-2 border-b border-gray-200">
                <span className="w-2 h-2 rounded-full bg-yonex-blue"></span>
                <h4 className="font-heading font-bold text-lg text-yonex-dark tracking-wide">
                  TENNIS RACQUETS
                </h4>
              </div>
              <ul className="space-y-2.5 text-sm font-medium">
                <li>
                  <a href="#ezone" className="text-gray-700 hover:text-yonex-blue hover:translate-x-1 transition-all flex items-center justify-between">
                    <span>EZONE Series</span>
                    <span className="text-xs bg-blue-50 text-yonex-blue px-2 py-0.5 rounded font-semibold uppercase">Power</span>
                  </a>
                </li>
                <li>
                  <a href="#vcore" className="text-gray-700 hover:text-yonex-blue hover:translate-x-1 transition-all flex items-center justify-between">
                    <span>VCORE Series</span>
                    <span className="text-xs bg-red-50 text-red-600 px-2 py-0.5 rounded font-semibold uppercase">Spin</span>
                  </a>
                </li>
                <li>
                  <a href="#percept" className="text-gray-700 hover:text-yonex-blue hover:translate-x-1 transition-all flex items-center justify-between">
                    <span>PERCEPT Series</span>
                    <span className="text-xs bg-green-50 text-yonex-green px-2 py-0.5 rounded font-semibold uppercase">Control</span>
                  </a>
                </li>
                <li>
                  <a href="#astrel" className="text-gray-700 hover:text-yonex-blue hover:translate-x-1 transition-all">
                    ASTREL Comfort Series
                  </a>
                </li>
                <li>
                  <a href="#junior-tennis" className="text-gray-700 hover:text-yonex-blue hover:translate-x-1 transition-all">
                    Junior Tennis Racquets
                  </a>
                </li>
                <li className="pt-2">
                  <a href="#all-tennis-racquets" className="text-yonex-blue font-bold flex items-center gap-1 hover:underline">
                    View All Tennis Racquets <ChevronRight size={14} />
                  </a>
                </li>
              </ul>
            </div>

            {/* Column 2: Badminton */}
            <div className="col-span-3 border-r border-gray-100 pr-6">
              <div className="flex items-center gap-2 mb-4 pb-2 border-b border-gray-200">
                <span className="w-2 h-2 rounded-full bg-yonex-green"></span>
                <h4 className="font-heading font-bold text-lg text-yonex-dark tracking-wide">
                  BADMINTON RACQUETS
                </h4>
              </div>
              <ul className="space-y-2.5 text-sm font-medium">
                <li>
                  <a href="#astrox" className="text-gray-700 hover:text-yonex-green hover:translate-x-1 transition-all flex items-center justify-between">
                    <span>ASTROX Series</span>
                    <span className="text-xs bg-red-50 text-yonex-red px-2 py-0.5 rounded font-semibold uppercase">Attack</span>
                  </a>
                </li>
                <li>
                  <a href="#nanoflare" className="text-gray-700 hover:text-yonex-green hover:translate-x-1 transition-all flex items-center justify-between">
                    <span>NANOFLARE Series</span>
                    <span className="text-xs bg-yellow-50 text-yellow-700 px-2 py-0.5 rounded font-semibold uppercase">Speed</span>
                  </a>
                </li>
                <li>
                  <a href="#arcsaber" className="text-gray-700 hover:text-yonex-green hover:translate-x-1 transition-all flex items-center justify-between">
                    <span>ARCSABER Series</span>
                    <span className="text-xs bg-blue-50 text-yonex-blue px-2 py-0.5 rounded font-semibold uppercase">Precision</span>
                  </a>
                </li>
                <li>
                  <a href="#muscle-power" className="text-gray-700 hover:text-yonex-green hover:translate-x-1 transition-all">
                    Muscle Power & Beginner Series
                  </a>
                </li>
                <li>
                  <a href="#junior-badminton" className="text-gray-700 hover:text-yonex-green hover:translate-x-1 transition-all">
                    Junior Badminton Racquets
                  </a>
                </li>
                <li className="pt-2">
                  <a href="#all-badminton-racquets" className="text-yonex-green font-bold flex items-center gap-1 hover:underline">
                    View All Badminton Racquets <ChevronRight size={14} />
                  </a>
                </li>
              </ul>
            </div>

            {/* Column 3: Featured Tennis Card */}
            <div className="col-span-3">
              <div className="group relative overflow-hidden rounded-sm bg-gray-50 border border-gray-100 hover:shadow-lg transition-all duration-300">
                <div className="aspect-[4/3] overflow-hidden bg-gray-100">
                  <img 
                    src="https://us.yonex.com/cdn/shop/files/Percept_Midnight_Nav_Thumbnail.jpg?v=1755816455&width=533" 
                    alt="PERCEPT Series"
                    className="w-full h-full object-cover object-center group-hover:scale-105 transition-transform duration-500"
                  />
                </div>
                <div className="p-4">
                  <span className="text-xs font-bold text-yonex-blue uppercase tracking-wider">Spotlight</span>
                  <h5 className="font-heading font-bold text-base text-yonex-dark mt-0.5">PERCEPT SERIES</h5>
                  <p className="text-xs text-gray-600 mt-1">Crush with Control. For the discerning tactician.</p>
                  <a href="#percept" className="inline-flex items-center gap-1 text-xs font-bold text-yonex-dark uppercase mt-3 group-hover:text-yonex-blue transition-colors">
                    Explore PERCEPT <ArrowRight size={12} />
                  </a>
                </div>
              </div>
            </div>

            {/* Column 4: Featured Badminton Card */}
            <div className="col-span-3">
              <div className="group relative overflow-hidden rounded-sm bg-gray-50 border border-gray-100 hover:shadow-lg transition-all duration-300">
                <div className="aspect-[4/3] overflow-hidden bg-gray-100">
                  <img 
                    src="https://us.yonex.com/cdn/shop/files/NAV_Racquets_Astrox_D.jpg?v=1741029061&width=533" 
                    alt="ASTROX Series"
                    className="w-full h-full object-cover object-center group-hover:scale-105 transition-transform duration-500"
                  />
                </div>
                <div className="p-4">
                  <span className="text-xs font-bold text-yonex-green uppercase tracking-wider">Spotlight</span>
                  <h5 className="font-heading font-bold text-base text-yonex-dark mt-0.5">ASTROX 99 PRO</h5>
                  <p className="text-xs text-gray-600 mt-1">Caution: Pure Power. Unstoppable steep smashes.</p>
                  <a href="#astrox" className="inline-flex items-center gap-1 text-xs font-bold text-yonex-dark uppercase mt-3 group-hover:text-yonex-green transition-colors">
                    Explore ASTROX <ArrowRight size={12} />
                  </a>
                </div>
              </div>
            </div>
          </div>
        )}

        {activeMenu === 'sports' && (
          <div className="grid grid-cols-5 gap-6">
            {[
              {
                title: "TENNIS",
                icon: <Target className="text-yonex-blue" size={18} />,
                items: ["Racquets", "Strings (PolyTour)", "Tournament Bags", "Footwear", "Apparel & Accessories"],
                link: "#tennis"
              },
              {
                title: "BADMINTON",
                icon: <Zap className="text-yonex-green" size={18} />,
                items: ["Racquets", "Shuttlecocks (Aerosensa)", "Badminton Strings", "Footwear", "Court Gear"],
                link: "#badminton"
              },
              {
                title: "GOLF",
                icon: <Shield className="text-amber-600" size={18} />,
                items: ["Forged Titanium Irons", "Royal EZONE Drivers", "Carbon Graphite Shafts", "Golf Bags & Towels"],
                link: "#golf"
              },
              {
                title: "PICKLEBALL",
                icon: <Award className="text-blue-500" size={18} />,
                items: ["Pro Tour Paddles", "Pickleball Footwear", "Court Essentials", "Performance Balls"],
                link: "#pickleball"
              },
              {
                title: "SNOWBOARDING",
                icon: <Zap className="text-indigo-600" size={18} />,
                items: ["High-Modulus Snowboards", "Step-in Bindings", "All-Mountain Gear", "Accessories"],
                link: "#snowboarding"
              }
            ].map((sport, i) => (
              <div key={i} className="p-4 bg-gray-50/60 rounded border border-gray-100 hover:border-yonex-blue transition-all">
                <div className="flex items-center gap-2 mb-3 pb-2 border-b border-gray-200">
                  {sport.icon}
                  <h4 className="font-heading font-bold text-base text-yonex-dark tracking-wider">
                    {sport.title}
                  </h4>
                </div>
                <ul className="space-y-2 text-xs font-medium text-gray-600">
                  {sport.items.map((sub, j) => (
                    <li key={j}>
                      <a href={sport.link} className="hover:text-yonex-blue transition-colors flex items-center gap-1.5">
                        <span className="w-1 h-1 bg-gray-300 rounded-full"></span>
                        <span>{sub}</span>
                      </a>
                    </li>
                  ))}
                </ul>
              </div>
            ))}
          </div>
        )}

        {(activeMenu === 'men' || activeMenu === 'women') && (
          <div className="grid grid-cols-4 gap-8">
            <div className="space-y-3">
              <h4 className="font-heading font-bold text-base text-yonex-dark border-b pb-2">
                {activeMenu.toUpperCase()}'S APPAREL
              </h4>
              <ul className="space-y-2 text-sm text-gray-700 font-medium">
                <li><a href="#tops" className="hover:text-yonex-blue">T-Shirts & Tops</a></li>
                <li><a href="#shorts" className="hover:text-yonex-blue">Shorts & Skirts</a></li>
                <li><a href="#warmups" className="hover:text-yonex-blue">Warm-Up Jackets & Pants</a></li>
                <li><a href="#tournament" className="hover:text-yonex-blue">The Tournament Series</a></li>
                <li><a href="#all-apparel" className="text-yonex-blue font-bold">Shop All Apparel &rarr;</a></li>
              </ul>
            </div>

            <div className="space-y-3">
              <h4 className="font-heading font-bold text-base text-yonex-dark border-b pb-2">
                FOOTWEAR & SHOES
              </h4>
              <ul className="space-y-2 text-sm text-gray-700 font-medium">
                <li><a href="#tennis-shoes" className="hover:text-yonex-blue">Tennis Shoes (Power Cushion)</a></li>
                <li><a href="#badminton-shoes" className="hover:text-yonex-blue">Badminton Court Shoes</a></li>
                <li><a href="#clay-shoes" className="hover:text-yonex-blue">Clay Court Shoes</a></li>
                <li><a href="#running-shoes" className="hover:text-yonex-blue">Cross Training & Running</a></li>
                <li><a href="#all-shoes" className="text-yonex-blue font-bold">Shop All Shoes &rarr;</a></li>
              </ul>
            </div>

            <div className="space-y-3">
              <h4 className="font-heading font-bold text-base text-yonex-dark border-b pb-2">
                ACCESSORIES
              </h4>
              <ul className="space-y-2 text-sm text-gray-700 font-medium">
                <li><a href="#bags" className="hover:text-yonex-blue">Bags & Backpacks</a></li>
                <li><a href="#socks" className="hover:text-yonex-blue">Socks & Headwear</a></li>
                <li><a href="#grips" className="hover:text-yonex-blue">Overgrips (Super Grap)</a></li>
                <li><a href="#towels" className="hover:text-yonex-blue">Sports Towels & Dampeners</a></li>
              </ul>
            </div>

            <div className="bg-gray-100 rounded overflow-hidden">
              <img 
                src="https://us.yonex.com/cdn/shop/files/Main_Nav_Thumbnail_NatureSeries.jpg?v=1789586447&width=533" 
                alt="Nature Series" 
                className="w-full h-36 object-cover"
              />
              <div className="p-3">
                <span className="text-[10px] font-bold text-yonex-green uppercase">Featured Line</span>
                <h5 className="font-heading font-bold text-sm text-yonex-dark">The Nature Series</h5>
                <p className="text-xs text-gray-600 mt-1">Earth tones meets athletic motion.</p>
                <a href="#nature-series" className="text-xs font-bold text-yonex-blue uppercase mt-2 inline-block">
                  Shop Collection &rarr;
                </a>
              </div>
            </div>
          </div>
        )}

        {activeMenu === 'athletes' && (
          <div className="grid grid-cols-4 gap-6">
            {[
              {
                name: "Stan Wawrinka",
                achieve: "3x Grand Slam Champion",
                racquet: "VCORE 95",
                img: "https://us.yonex.com/cdn/shop/files/HP_FeaturedAthlete_Stan_Gradient.jpg?v=1788564465&width=600"
              },
              {
                name: "Alex Lanier",
                achieve: "World Champion 2026",
                racquet: "ASTROX 99 PRO",
                img: "https://us.yonex.com/cdn/shop/files/HP_FeaturedAthlete_AlexLanier.jpg?v=1788564463&width=600"
              },
              {
                name: "Ben Shelton",
                achieve: "ATP Tour Phenomenon",
                racquet: "EZONE 98",
                img: "https://us.yonex.com/cdn/shop/files/Ben_950x950_14809034-9678-4145-9e79-a889cf0ff8e3.png?v=1745457224&width=600"
              },
              {
                name: "Jessica Pegula",
                achieve: "WTA Top 5 Star",
                racquet: "EZONE 98",
                img: "https://us.yonex.com/cdn/shop/files/Jessica_950x950_9c62a0aa-bfc4-456d-b6ff-dfc87d659af2.png?v=1745457224&width=600"
              }
            ].map((ath, idx) => (
              <div key={idx} className="group border border-gray-100 rounded overflow-hidden bg-gray-50 hover:shadow-md transition-all">
                <div className="h-44 overflow-hidden bg-gray-200">
                  <img src={ath.img} alt={ath.name} className="w-full h-full object-cover group-hover:scale-105 transition-transform duration-300" />
                </div>
                <div className="p-3">
                  <span className="text-[10px] font-bold text-gray-500 uppercase">{ath.achieve}</span>
                  <h5 className="font-heading font-bold text-base text-yonex-dark">{ath.name}</h5>
                  <p className="text-xs text-yonex-blue font-semibold mt-0.5">Weapon: {ath.racquet}</p>
                </div>
              </div>
            ))}
          </div>
        )}

        {activeMenu === 'about' && (
          <div className="grid grid-cols-12 gap-8 items-center">
            <div className="col-span-7 space-y-3">
              <span className="text-xs font-bold text-yonex-blue uppercase tracking-widest">Since 1946 • Niigata, Japan</span>
              <h3 className="font-heading font-black text-2xl text-yonex-dark">
                DRIVEN BY TECHNOLOGY, CRAFTED BY PASSION
              </h3>
              <p className="text-sm text-gray-600 leading-relaxed">
                At Yonex, we constantly explore innovative materials, aerodynamic geometries, and precision craftsmanship to deliver the finest racquets, equipment, and footwear in the world. From the revolutionary ISOMETRIC™ head shape to 2G-Namd™ graphite, every detail is engineered to elevate your game.
              </p>
              <div className="flex gap-4 pt-2">
                <a href="#made-by-yonex" className="bg-yonex-blue text-white px-4 py-2 text-xs font-bold uppercase tracking-wider rounded hover:bg-yonex-darkBlue transition-colors">
                  Made by Yonex
                </a>
                <a href="#technology" className="border border-yonex-dark text-yonex-dark px-4 py-2 text-xs font-bold uppercase tracking-wider rounded hover:bg-gray-100 transition-colors">
                  Our Technology
                </a>
              </div>
            </div>
            <div className="col-span-5">
              <img 
                src="https://us.yonex.com/cdn/shop/files/TOKITO_ODA.jpg?v=1784579463&width=800" 
                alt="Yonex Craftsmanship" 
                className="rounded shadow-md w-full h-52 object-cover"
              />
            </div>
          </div>
        )}
      </div>
    </div>
  );
}
