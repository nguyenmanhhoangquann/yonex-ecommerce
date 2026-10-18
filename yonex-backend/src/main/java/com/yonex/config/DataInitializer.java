package com.yonex.config;

import com.yonex.entity.*;
import com.yonex.enums.Role;
import com.yonex.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final SportRepository sportRepository;
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final ProductVariantRepository variantRepository;
    private final ProductImageRepository imageRepository;
    private final ProductSpecRepository specRepository;
    private final AthleteRepository athleteRepository;
    private final StoreRepository storeRepository;
    private final FaqRepository faqRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(String... args) {
        seedUsers();
        seedSportsAndCategories();
        seedProducts();
        seedAthletes();
        seedStores();
        seedFaqs();
        log.info("Yonex Demo Data Initialized Successfully.");
    }

    private void seedUsers() {
        if (!userRepository.existsByEmail("admin@yonex-demo.com")) {
            User admin = User.builder()
                    .email("admin@yonex-demo.com")
                    .password(passwordEncoder.encode("Admin@123456"))
                    .fullName("Yonex Administrator")
                    .phone("0901234567")
                    .role(Role.ROLE_ADMIN)
                    .active(true)
                    .build();
            userRepository.save(admin);
            log.info("Created demo ADMIN: admin@yonex-demo.com / Admin@123456");
        }

        if (!userRepository.existsByEmail("customer@yonex-demo.com")) {
            User customer = User.builder()
                    .email("customer@yonex-demo.com")
                    .password(passwordEncoder.encode("Customer@123456"))
                    .fullName("Nguyễn Hoàng Quân")
                    .phone("0987654321")
                    .role(Role.ROLE_CUSTOMER)
                    .active(true)
                    .build();
            userRepository.save(customer);
            log.info("Created demo CUSTOMER: customer@yonex-demo.com / Customer@123456");
        }
    }

    private void seedSportsAndCategories() {
        if (sportRepository.count() == 0) {
            Sport badminton = sportRepository.save(Sport.builder()
                    .name("Badminton")
                    .slug("badminton")
                    .description("Yonex world-leading badminton equipment, used by top world champions.")
                    .imageUrl("https://us.yonex.com/cdn/shop/files/NAV_Racquets_Astrox_D.jpg?v=1741029061&width=800")
                    .active(true)
                    .build());

            Sport tennis = sportRepository.save(Sport.builder()
                    .name("Tennis")
                    .slug("tennis")
                    .description("Innovative tennis frames and precision string technology.")
                    .imageUrl("https://us.yonex.com/cdn/shop/files/EZ08100_Blast_Blue_1.jpg?v=1739910270&width=800")
                    .active(true)
                    .build());

            Sport golf = sportRepository.save(Sport.builder()
                    .name("Golf")
                    .slug("golf")
                    .description("Advanced graphite shaft engineering and carbon composite golf technology.")
                    .active(true)
                    .build());

            Sport running = sportRepository.save(Sport.builder()
                    .name("Running")
                    .slug("running")
                    .description("Power Cushion equipped footwear for superior shock absorption.")
                    .active(true)
                    .build());

            categoryRepository.save(Category.builder().name("Tennis Racquets").slug("tennis-racquets").sport(tennis).active(true).build());
            categoryRepository.save(Category.builder().name("Badminton Racquets").slug("badminton-racquets").sport(badminton).active(true).build());
            categoryRepository.save(Category.builder().name("Racquets").slug("racquets").sport(badminton).active(true).build());
            categoryRepository.save(Category.builder().name("Footwear").slug("shoes").sport(badminton).active(true).build());
            categoryRepository.save(Category.builder().name("Apparel").slug("apparel").sport(badminton).active(true).build());
            categoryRepository.save(Category.builder().name("Bags").slug("bags").sport(badminton).active(true).build());
            categoryRepository.save(Category.builder().name("Strings").slug("strings").sport(badminton).active(true).build());
            categoryRepository.save(Category.builder().name("Accessories").slug("accessories").sport(badminton).active(true).build());
        }
    }

    private void seedProducts() {
        if (productRepository.count() == 0) {
            Sport tennis = sportRepository.findBySlug("tennis").orElse(null);
            Sport badminton = sportRepository.findBySlug("badminton").orElse(null);
            Category racquets = categoryRepository.findBySlug("racquets").orElse(null);
            Category shoes = categoryRepository.findBySlug("shoes").orElse(null);
            Category bags = categoryRepository.findBySlug("bags").orElse(null);
            Category strings = categoryRepository.findBySlug("strings").orElse(null);
            Category accessories = categoryRepository.findBySlug("accessories").orElse(null);

            // 1. EZONE 100
            createProductWithDetails(
                    "EZ100-2026", "EZONE 100", "ezone-100",
                    "For all-around players looking for easy power and comfort in an explosive, maneuverable frame.",
                    "Equipped with 2G-Namd Speed and Vibration Dampening Mesh (VDM), the 8th-generation EZONE delivers plush feel and blistering speed on groundstrokes.",
                    new BigDecimal("305.00"), new BigDecimal("325.00"),
                    tennis, racquets, "EZONE", "Best Seller", 4.9, 142, true, false,
                    List.of(
                            new String[]{"Espresso Brown", "#5B3B2A", "4 3/8", "EZ100-BRN", "305.00", "45", "https://us.yonex.com/cdn/shop/files/ALL_08EZ100_839-1.jpg?v=1785871338&width=800"},
                            new String[]{"Blast Blue", "#3A73E7", "4 3/8", "EZ100-BLU", "305.00", "50", "https://us.yonex.com/cdn/shop/files/EZ08100_Blast_Blue_1.jpg?v=1739910270&width=800"}
                    ),
                    List.of(
                            new String[]{"Head Size", "100 sq.in."},
                            new String[]{"Weight", "300 g / 10.6 oz"},
                            new String[]{"Length", "27 in."},
                            new String[]{"Balance Point", "320 mm"},
                            new String[]{"Material", "HM GRAPHITE / 2G-Namd™ SPEED / VDM"},
                            new String[]{"String Pattern", "16 x 19"}
                    )
            );

            // 2. EZONE 98
            createProductWithDetails(
                    "EZ98-2026", "EZONE 98", "ezone-98",
                    "For aggressive tournament and advanced players looking for laser pinpoint precision.",
                    "Controlled power with supreme comfort and surgical precision for attacking competitors.",
                    new BigDecimal("305.00"), new BigDecimal("330.00"),
                    tennis, racquets, "EZONE", "Tour Choice", 4.8, 98, true, true,
                    List.of(
                            new String[]{"Blast Blue", "#3A73E7", "4 1/4", "EZ98-BLU", "305.00", "30", "https://us.yonex.com/cdn/shop/files/EZ0898_BlastBlue_5868.jpg?v=1739481973&width=800"},
                            new String[]{"Espresso Brown", "#5B3B2A", "4 3/8", "EZ98-BRN", "305.00", "25", "https://us.yonex.com/cdn/shop/files/ALL_08EZ98_839-2.jpg?v=1786149524&width=800"}
                    ),
                    List.of(
                            new String[]{"Head Size", "98 sq.in."},
                            new String[]{"Weight", "305 g / 10.8 oz"},
                            new String[]{"Balance Point", "315 mm"},
                            new String[]{"String Pattern", "16 x 19"}
                    )
            );

            // 3. ASTROX 99 PRO
            createProductWithDetails(
                    "AX99P-2026", "ASTROX 99 PRO", "astrox-99-pro",
                    "Equipped with the Rotational Generator System and Power-Assist Bumper, delivering crushing steep smashes.",
                    "Developed in collaboration with world singles titans, ASTROX 99 PRO delivers maximum power transfer and devastating attack angles.",
                    new BigDecimal("270.00"), new BigDecimal("290.00"),
                    badminton, racquets, "ASTROX", "World Champion", 5.0, 86, true, false,
                    List.of(
                            new String[]{"Cherry Sunburst", "#B7002A", "4U G5", "AX99P-CS", "270.00", "28", "https://us.yonex.com/cdn/shop/files/NAV_Racquets_Astrox_D.jpg?v=1741029061&width=800"},
                            new String[]{"White Tiger", "#F3F4F6", "3U G5", "AX99P-WT", "270.00", "15", "https://us.yonex.com/cdn/shop/files/NAV_Racquets_Nanoflare_D.jpg?v=1741029124&width=800"}
                    ),
                    List.of(
                            new String[]{"Flex", "Extra Stiff"},
                            new String[]{"Frame", "HM Graphite + VOLUME CUT RESIN + Tungsten"},
                            new String[]{"Shaft", "HM Graphite + Namd"},
                            new String[]{"Balance", "Head Heavy"},
                            new String[]{"Stringing Advice", "4U: 20 - 28 lbs, 3U: 21 - 29 lbs"}
                    )
            );

            // 4. NANOFLARE 1000 Z
            createProductWithDetails(
                    "NF1000Z", "NANOFLARE 1000 Z", "nanoflare-1000-z",
                    "Designed for players seeking rapid smash speeds and swift maneuverability with Sonic Flare System.",
                    "Record-breaking smash speed racquet with ultra-aerodynamic AERO Frame for lightning reflexes.",
                    new BigDecimal("280.00"), new BigDecimal("300.00"),
                    badminton, racquets, "NANOFLARE", "Lightning Speed", 4.9, 114, true, true,
                    Collections.singletonList(
                            new String[]{"Lightning Yellow", "#EAB308", "4U G5", "NF1000Z-YL", "280.00", "35", "https://us.yonex.com/cdn/shop/files/NAV_Racquets_Nanoflare_D.jpg?v=1741029124&width=800"}
                    ),
                    List.of(
                            new String[]{"Flex", "Extra Stiff"},
                            new String[]{"Frame", "HM Graphite, NANOMETRIC DR, M40X, EX-HYPER MG"},
                            new String[]{"Shaft", "HM Graphite, Ultra PE FIBER"},
                            new String[]{"Balance", "Head Light"}
                    )
            );

            // 5. ARCSABER 11 PRO
            createProductWithDetails(
                    "ARC11P", "ARCSABER 11 PRO", "arcsaber-11-pro",
                    "Decisive control with enhanced shuttle hold for laser precision placement.",
                    "Enhanced frame stability and pocketing booster technology that lets you hold the shuttle for precision counter-attacks.",
                    new BigDecimal("260.00"), new BigDecimal("280.00"),
                    badminton, racquets, "ARCSABER", "All-Around Control", 4.8, 92, true, false,
                    Collections.singletonList(
                            new String[]{"Grayish Pearl", "#9CA3AF", "4U G5", "ARC11P-GP", "260.00", "40", "https://us.yonex.com/cdn/shop/files/NAV_Racquets_Arcsaber_D.jpg?v=1741029193&width=800"}
                    ),
                    List.of(
                            new String[]{"Flex", "Stiff"},
                            new String[]{"Balance", "Even Balance"},
                            new String[]{"Frame", "HM Graphite + POCKETING BOOSTER"}
                    )
            );

            // 6. VCORE 98
            createProductWithDetails(
                    "VC98-2026", "VCORE 98", "vcore-98",
                    "The ultimate spin monster racquet engineered for heavy high-trajectory topspin.",
                    "Equipped with 2G-Namd Flex Force and Silicone Oil Infused Grommets for rapid snapback and vicious heavy ball spin.",
                    new BigDecimal("289.00"), new BigDecimal("310.00"),
                    tennis, racquets, "VCORE", "Spin Monster", 4.9, 75, true, false,
                    Collections.singletonList(
                            new String[]{"Scarlet Red", "#DC2626", "4 3/8", "VC98-RED", "289.00", "22", "https://us.yonex.com/cdn/shop/files/VC98_Red_1.jpg?v=1739481973&width=800"}
                    ),
                    List.of(
                            new String[]{"Head Size", "98 sq.in."},
                            new String[]{"Weight", "305 g / 10.8 oz"},
                            new String[]{"String Pattern", "16 x 19"}
                    )
            );

            // 7. POWER CUSHION 65 Z3
            createProductWithDetails(
                    "SHB65Z3", "POWER CUSHION 65 Z3", "power-cushion-65-z3",
                    "The undisputed all-around footwear choice of world championship badminton professionals.",
                    "Featuring Radial Blade Sole and Power Cushion+ technology that converts impact energy into explosive rebounding power.",
                    new BigDecimal("140.00"), new BigDecimal("155.00"),
                    badminton, shoes, "POWER CUSHION", "Pro Standard", 4.9, 130, false, false,
                    Collections.singletonList(
                            new String[]{"White / Red", "#FFFFFF", "42 EU", "SHB65Z3-WR-42", "140.00", "18", "https://us.yonex.com/cdn/shop/files/NAV_Footwear_Badminton_D.jpg?v=1741029318&width=800"}
                    ),
                    List.of(
                            new String[]{"Upper", "Synthetic Leather"},
                            new String[]{"Midsole", "Synthetic Resin with Power Cushion+"},
                            new String[]{"Outsole", "Radial Blade Rubber Sole"}
                    )
            );

            // 8. BG80 POWER
            createProductWithDetails(
                    "BG80P", "BG80 POWER Badminton String", "bg80-power",
                    "High-modulus Vectran fibers provide an explosive smash feel and crisp metallic sound.",
                    "The favorite hard-feeling string for tournament smashers and high-tension stringers.",
                    new BigDecimal("14.00"), new BigDecimal("16.00"),
                    badminton, strings, "STRINGS", "Best Seller", 4.9, 210, false, false,
                    Collections.singletonList(
                            new String[]{"White", "#FFFFFF", "0.68mm", "BG80P-WH", "14.00", "200", "https://us.yonex.com/cdn/shop/files/BG80P_White.jpg?v=1739481973&width=800"}
                    ),
                    List.of(
                            new String[]{"Gauge", "0.68 mm / 22 GA"},
                            new String[]{"Length", "10 m / 33 ft"},
                            new String[]{"Core", "High-Intensity Nylon Multifilament with Vectran™"}
                    )
            );
        }
    }

    private void createProductWithDetails(
            String sku, String name, String slug, String shortDesc, String desc,
            BigDecimal price, BigDecimal origPrice, Sport sport, Category category,
            String series, String badge, double rating, int reviewsCount,
            boolean isFeatured, boolean isNew,
            List<String[]> variants, List<String[]> specs
    ) {
        Product p = Product.builder()
                .sku(sku)
                .name(name)
                .slug(slug)
                .shortDescription(shortDesc)
                .description(desc)
                .price(price)
                .originalPrice(origPrice)
                .sport(sport)
                .category(category)
                .series(series)
                .badge(badge)
                .rating(rating)
                .reviewsCount(reviewsCount)
                .isFeatured(isFeatured)
                .isNew(isNew)
                .active(true)
                .build();

        p = productRepository.save(p);

        List<ProductVariant> pvList = new ArrayList<>();
        List<ProductImage> piList = new ArrayList<>();
        int imgOrder = 0;

        for (String[] v : variants) {
            ProductVariant pv = ProductVariant.builder()
                    .product(p)
                    .colorName(v[0])
                    .colorCode(v[1])
                    .size(v[2])
                    .sku(v[3])
                    .price(new BigDecimal(v[4]))
                    .stockQuantity(Integer.parseInt(v[5]))
                    .imageUrl(v[6])
                    .build();
            pvList.add(pv);

            ProductImage pi = ProductImage.builder()
                    .product(p)
                    .imageUrl(v[6])
                    .isPrimary(imgOrder == 0)
                    .displayOrder(imgOrder++)
                    .build();
            piList.add(pi);
        }
        variantRepository.saveAll(pvList);
        imageRepository.saveAll(piList);

        List<ProductSpec> specList = new ArrayList<>();
        int specOrder = 0;
        for (String[] s : specs) {
            specList.add(ProductSpec.builder()
                    .product(p)
                    .specKey(s[0])
                    .specValue(s[1])
                    .displayOrder(specOrder++)
                    .build());
        }
        specRepository.saveAll(specList);
    }

    private void seedAthletes() {
        if (athleteRepository.count() == 0) {
            athleteRepository.save(Athlete.builder()
                    .name("Stan Wawrinka")
                    .country("Switzerland")
                    .sport("Tennis")
                    .avatarUrl("https://us.yonex.com/cdn/shop/files/Stan_Wawrinka_Portrait.jpg?v=1739481973&width=800")
                    .bio("3-time Grand Slam Champion (Australian Open, Roland Garros, US Open) known for the most lethal one-handed backhand in modern tennis.")
                    .achievements("3x Grand Slam Champion, Olympic Gold Medalist")
                    .racquetsUsed("VCORE 98")
                    .active(true)
                    .build());

            athleteRepository.save(Athlete.builder()
                    .name("Alex Lanier")
                    .country("France")
                    .sport("Badminton")
                    .avatarUrl("https://us.yonex.com/cdn/shop/files/Alex_Lanier_Portrait.jpg?v=1739481973&width=800")
                    .bio("Sensational young French prodigy and Japan Open Super 750 Champion, representing the next era of offensive power play.")
                    .achievements("Japan Open Super 750 Champion (Youngest Winner)")
                    .racquetsUsed("ASTROX 88 D PRO")
                    .active(true)
                    .build());

            athleteRepository.save(Athlete.builder()
                    .name("Viktor Axelsen")
                    .country("Denmark")
                    .sport("Badminton")
                    .avatarUrl("https://us.yonex.com/cdn/shop/files/Viktor_Axelsen.jpg?v=1739481973&width=800")
                    .bio("2-time Olympic Gold Medalist (Tokyo 2020 & Paris 2024), World Champion, and dominant World No. 1.")
                    .achievements("2x Olympic Gold Champion, 2x World Champion")
                    .racquetsUsed("ASTROX 100 ZZ")
                    .active(true)
                    .build());

            athleteRepository.save(Athlete.builder()
                    .name("Elena Rybakina")
                    .country("Kazakhstan")
                    .sport("Tennis")
                    .avatarUrl("https://us.yonex.com/cdn/shop/files/Elena_Rybakina.jpg?v=1739481973&width=800")
                    .bio("Wimbledon Champion renowned for her blistering serves and calm, powerful baseline rallies.")
                    .achievements("Wimbledon Champion 2022")
                    .racquetsUsed("VCORE 100")
                    .active(true)
                    .build());
        }
    }

    private void seedStores() {
        if (storeRepository.count() == 0) {
            storeRepository.save(Store.builder()
                    .name("Yonex Flagship Store Hanoi")
                    .address("123 Phố Huế, Quận Hai Bà Trưng")
                    .city("Hà Nội")
                    .phone("024 3822 9999")
                    .email("hanoi@yonex-vietnam.demo")
                    .openHours("08:30 - 21:30 (Thứ 2 - Chủ Nhật)")
                    .latitude(21.0175)
                    .longitude(105.8522)
                    .mapUrl("https://maps.google.com/?q=21.0175,105.8522")
                    .active(true)
                    .build());

            storeRepository.save(Store.builder()
                    .name("Yonex Pro Shop TP. Hồ Chí Minh")
                    .address("456 Nguyễn Đình Chiểu, Phường 4, Quận 3")
                    .city("Hồ Chí Minh")
                    .phone("028 3833 8888")
                    .email("hcm@yonex-vietnam.demo")
                    .openHours("09:00 - 21:00 (Thứ 2 - Chủ Nhật)")
                    .latitude(10.7712)
                    .longitude(106.6854)
                    .mapUrl("https://maps.google.com/?q=10.7712,106.6854")
                    .active(true)
                    .build());

            storeRepository.save(Store.builder()
                    .name("Yonex Tokyo Showroom")
                    .address("1-1-1 Shinbashi, Minato-ku")
                    .city("Tokyo")
                    .phone("+81 3-3836-1221")
                    .openHours("10:00 - 20:00")
                    .latitude(35.6663)
                    .longitude(139.7583)
                    .active(true)
                    .build());
        }
    }

    private void seedFaqs() {
        if (faqRepository.count() == 0) {
            faqRepository.save(Faq.builder()
                    .question("Làm thế nào để phân biệt vợt Yonex chính hãng và hàng giả?")
                    .answer("Vợt Yonex chính hãng phân phối tại Việt Nam luôn có tem cào chống hàng giả Sunrise với lớp phủ bạc để tra cứu mã số bảo mật, nắp cán có mã laser sắc nét (mã số thân và mã số chóp nắp chứa mã ngày tháng/năm sản xuất và mã quốc gia SP/JP), và logo Yonex sắc sảo không bị nhòe mép.")
                    .category("Phân biệt thật giả")
                    .displayOrder(1)
                    .active(true)
                    .build());

            faqRepository.save(Faq.builder()
                    .question("Nên căng vợt bao nhiêu lbs (kg) là phù hợp?")
                    .answer("Đối với người mới chơi: Khuyên dùng 20 - 22 lbs (~9 - 10 kg) để trợ lực tốt và êm tay. Người chơi phong trào trung bình: 23 - 25 lbs (~10.5 - 11.5 kg). Vận động viên và người chơi chuyên nghiệp: 26 - 30+ lbs (~12 - 14 kg). Tuyệt đối không căng vượt quá mức 'Stringing Advice' khắc trên thân/chóp vợt để tránh nứt gãy khung.")
                    .category("Kỹ thuật & Dây cước")
                    .displayOrder(2)
                    .active(true)
                    .build());

            faqRepository.save(Faq.builder()
                    .question("Chính sách bảo hành khung vợt Yonex như thế nào?")
                    .answer("Khung vợt Yonex chính hãng được bảo hành 90 ngày kể từ ngày mua đối với các lỗi do nhà sản xuất (nứt, gãy tự nhiên không do va chạm hoặc va chạm với vợt đối thủ, không do đan dây sai kỹ thuật hoặc quá số lbs quy định).")
                    .category("Chính sách bảo hành")
                    .displayOrder(3)
                    .active(true)
                    .build());
        }
    }
}
