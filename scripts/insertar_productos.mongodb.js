
/* global use, db */

// Base de datos de Nostalgia
use('tienda_alternativa_P3');

// Colección de productos
const productos = db.getCollection('productos');

// Agregar los 9 productos originales
productos.insertMany([
  {
    nombre: 'Abrigo Otoño',
    categoria: 'Chaquetas',
    precio: 35000,
    emoji: '✦',
    color: 'product-wine',
    detalle: 'ESSENTIALS / 01',
    imagen: '/ropa/image1.png',
    descuento: 0
  },
  {
    nombre: 'Corset Bonnie',
    categoria: 'Poleras',
    precio: 18990,
    emoji: '♜',
    color: 'product-stone',
    detalle: 'DENIM / 02',
    imagen: '/ropa/image2.png',
    descuento: 0
  },
  {
    nombre: 'Blusa Primaveral',
    categoria: 'Poleras',
    precio: 18990,
    emoji: '✦',
    color: 'product-black',
    detalle: 'AFTER DARK / 03',
    imagen: '/ropa/image3.png',
    descuento: 0
  },
  {
    nombre: 'Blusa Occidental',
    categoria: 'Poleras',
    precio: 15990,
    emoji: '♛',
    color: 'product-rose',
    detalle: 'ROMANTIC / 04',
    imagen: '/ropa/image4.png',
    descuento: 0
  },
  {
    nombre: 'Chaqueta de cuero Rose',
    categoria: 'Chaquetas',
    precio: 38990,
    emoji: '☾',
    color: 'product-wine',
    detalle: 'JEWELLERY / 05',
    imagen: '/ropa/image5.png',
    descuento: 0
  },
  {
    nombre: 'Pantalon Secret',
    categoria: 'Pantalones',
    precio: 27990,
    emoji: '✧',
    color: 'product-black',
    detalle: 'SHOES / 06',
    imagen: '/ropa/image7.png',
    descuento: 0
  },
  {
    nombre: 'Falda Cat',
    categoria: 'Pantalones',
    precio: 12990,
    emoji: '❋',
    color: 'product-rose',
    detalle: 'STATEMENT / 07',
    imagen: '/ropa/image8.png',
    descuento: 0
  },
  {
    nombre: 'Botas Cat',
    categoria: 'Zapatos',
    precio: 32990,
    emoji: '♡',
    color: 'product-stone',
    detalle: 'ACCESSORIES / 08',
    imagen: '/ropa/image9.png',
    descuento: 0
  },
  {
    nombre: 'Chaqueta gótica Nostalgia',
    categoria: 'Chaquetas',
    precio: 27990,
    emoji: '✦',
    color: 'product-stone',
    detalle: 'ADMIN / NUEVO',
    imagen: '/ropa/image5.png',
    descuento: 20
  }
]);

// Comprobar cuántos productos existen
console.log(
  'Productos registrados:',
  productos.countDocuments({})
);
