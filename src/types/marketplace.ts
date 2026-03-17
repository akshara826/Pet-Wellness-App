export type Category = "Food" | "Toys" | "Medicine" | "Accessories";

export type ProductStatus = "In Stock" | "Low Stock" | "Out of Stock";

export interface Product {
  id: string;
  name: string;
  category: Category;
  price: number;
  stock: number;
  description: string;
  imageUrl: string;
  status: ProductStatus;
  createdDate: string;
}
