# class Node:
#     def __init__(self, value):
#         self.root = value
#         self.left = None
#         self.right = None
#
#     def add_leftChild(self, node):
#         if type(node) != type(Node(0)):
#             raise TypeError
#         else:
#             self.left = node
#
#     def add_rightChild(self, node):
#         if type(node) != type(Node(0)):
#             raise TypeError
#         else:
#             self.right = node
#
#     def __repr__(self):
#         return 'Node({!r}).format(self.root)'
#
#     def post_order(self):
#         yield self.root
#         if self.left != None:
#             yield from self.left.post_order()
#         if self.right != None:
#             yield from self.right.post_order()
#
#     def in_order(self):
#         if self.left != None:
#             yield from self.left.in_order()
#         yield self.root
#         if self.right != None:
#             yield from self.right.in_order()
#
#     def pre_order(self):
#         if self.left != None:
#             yield from self.left.pre_order()
#         if self.right != None:
#             yield from self.right.pre_order()
#         yield self.root
#
# if __name__ == "__main__":
#     root = Node(8)
#     node3 = Node(3)
#     node1 = Node(1)
#     node4 = Node(4)
#     node6 = Node(6)
#     node7 = Node(7)
#     node10 = Node(10)
#     node14 = Node(14)
#     node13 = Node(13)
#     root.add_leftChild(node3)
#     node3.add_leftChild(node1)
#     node3.add_rightChild(node6)
#     node6.add_leftChild(node4)
#     node6.add_rightChild(node7)
#     root.add_rightChild(node10)
#     node10.add_rightChild(node14)
#     node14.add_leftChild(node13)
#
#     print("\npost_order")
#     for node in root.post_order():
#         print(node, end=' ')
#     print('\nin_order')
#     for node in root.in_order():
#         print(node, end=' ')
#     print('\npre_order')
#     for node in root.pre_order():
#         print(node, end=' ')
#     print('')