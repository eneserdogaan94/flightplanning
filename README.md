# 🛫 Flight Planning System ✈️

Bu proje, kullanıcıların uçuşlarını planlayabileceği ve yönetebileceği **Full-Stack** bir uygulamadır. **Spring Boot (Java)** backend ve **React (MUI)** frontend teknolojileri kullanılarak geliştirilmiştir.

## 📌 Özellikler

- 🏢 Havalimanı ve uçuş bilgilerini listeleme
- 🔍 Uçuş arama ve filtreleme
- ✅ Admin paneli üzerinden uçuş ekleme ve düzenleme
- 🔄 Sayfalama (pagination) ve renk kodlamalı uçuş durumu gösterimi
- 🔑 JWT tabanlı kimlik doğrulama
- 🎨 **Material UI (MUI)** kullanılarak şık ve modern arayüz

---

## 🚀 Kurulum ve Çalıştırma

### **Backend (Spring Boot)**
1. **Projeyi klonlayın:**
2.	   git clone https://github.com/eneserdogaan94/flightplanning.git

Backend dizinine gidin ve bağımlılıkları yükleyin:
cd flightplanning/backend
mvn clean install
3.	Uygulamayı başlatın:
mvn spring-boot:run
4.	Varsayılan olarak aşağıdaki adreslerde çalışacaktır:
o	API URL: http://localhost:8080
________________________________________
Frontend (React)

git: https://github.com/eneserdogaan94/flightplanningreact
1.	Frontend dizinine gidin ve bağımlılıkları yükleyin:
cd flightplanning/frontend
npm install
2.	Çevre değişkenlerini ayarlayın (.env dosyası oluşturun):
REACT_APP_API_URL=http://localhost:8080
3.	Uygulamayı başlatın:
npm start
4.	Varsayılan olarak aşağıdaki adreslerde çalışacaktır:
o	Web Arayüzü: http://localhost:3000
________________________________________
📡 API Endpointleri
Kullanıcı İşlemleri
Endpoint	HTTP Metodu	Açıklama
/api/auth/login	POST	Kullanıcı girişi
/api/auth/register	POST	Yeni kullanıcı kaydı
Uçuş İşlemleri
Endpoint	HTTP Metodu	Açıklama
/api/flights	GET	Tüm uçuşları listeleme
/api/flights/saveFlight	POST	Yeni uçuş ekleme
/api/flights/searchById	GET	Kullanıcının uçuşlarını getir
________________________________________
🛠 Kullanılan Teknolojiler
Teknoloji	Açıklama
Frontend:	React.js, Material UI, Axios
Backend:	Spring Boot, Spring Security, JWT Authentication
Veritabanı:	MySQL

