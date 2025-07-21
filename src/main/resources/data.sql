
-- CATEGORY TABLE
INSERT INTO category (name, description) VALUES
('Fruits', 'Fresh and seasonal fruits'),
('Vegetables', 'Organic and green vegetables'),
('Dairy', 'Milk, cheese, and more');

-- SUPPLIER TABLE
INSERT INTO supplier (name, email, phone, address, contact_person) VALUES
('Fresh Farm Supplies', 'freshfarm@gmail.com', '9876543210', 'Village Road, Delhi', 'Anil Mehta'),
('Organic World', 'organicworld@gmail.com', '9123456789', 'Sector 10, Noida', 'Rekha Singh');

-- PRODUCT TABLE
INSERT INTO product (
  name, sku, description, price, quantity_in_stock, reorder_level, category_id,
  supplier_id, unit, is_active, created_at, updated_at
) VALUES 
('Apple', 'APL-001', 'Fresh Red Apples', 120.00, 50, 10, 1, 1, 'kg', true, now(), now()),
('Tomato', 'TMT-002', 'Farm Tomatoes', 40.00, 150, 20, 2, 2, 'kg', true, now(), now()),
('Milk', 'MLK-003', 'Full cream milk', 60.00, 100, 15, 3, 1, 'litre', true, now(), now());

-- INVENTORY TABLE
INSERT INTO inventory (product_id, stock_in, stock_out, adjusted_quantity, adjustment_reason) VALUES
(1, 60, 10, 50, 'Initial stock entry'),
(2, 160, 10, 150, 'Initial stock entry'),
(3, 110, 10, 100, 'Initial stock entry');

-- PURCHASE ORDER TABLE
INSERT INTO purchase_order (order_number, supplier_id, status, order_date, expected_delivery_date, total_amount) VALUES
('PO-1001', 1, 'PENDING', '2025-07-10', '2025-07-20', 6000.00),
('PO-1002', 2, 'DELIVERED', '2025-07-12', '2025-07-18', 3000.00);

-- PURCHASE ORDER ITEM TABLE
INSERT INTO purchase_order_item (purchase_order_id, product_id, ordered_quantity, received_quantity, unit_price) VALUES
(1, 1, 50, 0, 110.00),
(2, 2, 100, 100, 35.00);

