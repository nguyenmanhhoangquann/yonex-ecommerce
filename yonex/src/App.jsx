import React from 'react';
import { CartProvider } from './context/CartContext';
import AnnouncementBar from './components/AnnouncementBar';
import Header from './components/Header';
import HeroBanner from './components/HeroBanner';
import PromoCallout from './components/PromoCallout';
import SpotlightCategories from './components/SpotlightCategories';
import FeaturedCampaigns from './components/FeaturedCampaigns';
import IntelligentlyDesigned from './components/IntelligentlyDesigned';
import ShopBySport from './components/ShopBySport';
import FeaturedProducts from './components/FeaturedProducts';
import AthleteSpotlight from './components/AthleteSpotlight';
import Newsletter from './components/Newsletter';
import Footer from './components/Footer';
import CartDrawer from './components/CartDrawer';
import QuickViewModal from './components/QuickViewModal';
import SearchModal from './components/SearchModal';
import Toast from './components/Toast';

export default function App() {
  return (
    <CartProvider>
      <div className="min-h-screen flex flex-col bg-white">
        {/* Top utility notification */}
        <AnnouncementBar />

        {/* Sticky Header with Navigation & Mega Menu */}
        <Header />

        {/* Main Content Area */}
        <main className="flex-grow">
          {/* Hero Video Banner */}
          <HeroBanner />

          {/* Complimentary Balls Promo Callout */}
          <PromoCallout />

          {/* Spotlight Categories (The Nature Series, 80th Anniversary, Muse) */}
          <SpotlightCategories />

          {/* Featured Athlete Campaigns (Stan the Man & Alex Lanier) */}
          <FeaturedCampaigns />

          {/* Intelligently Designed 4-grid */}
          <IntelligentlyDesigned />

          {/* Shop By Sport Interactive Tab Switcher */}
          <ShopBySport />

          {/* Featured Gear & New Arrivals with Swatches */}
          <FeaturedProducts />

          {/* Team Yonex Featured Athletes */}
          <AthleteSpotlight />

          {/* Insider Newsletter Signup */}
          <Newsletter />
        </main>

        {/* Global Footer */}
        <Footer />

        {/* Interactive Overlays */}
        <CartDrawer />
        <QuickViewModal />
        <SearchModal />
        <Toast />
      </div>
    </CartProvider>
  );
}
