"""
minimize −7x1 + 7x2 − 2x3 − x4 − 6x5
subject to:
3x1 − x2 + x3 − 2x4 = −3
2x1 + x2 + x4 + x5 = 4
−x1 + 3x2 − 3x4 + x6 = 12
x i > 0,(i = 1,...,6)

answer:
x1.val = 0
x2.val = 0
x3.val = 0
x4.val = 1.5
x5.val = 2.5
x6.val = 16.5
obj.val = -16.5
"""
import numpy as np
from numpy.linalg import *

# 3个约束，4个未知数
M_rows = 3
N_cols = 7
# z为c第一列
c = [0, -7, 7, -2, -1, -6, 0]
# b为第一列
A = [
    [-3, 3, -1, 1, -2, 0, 0],
    [4, 2, 1, 0, 1, 1, 0],
    [12, -1, 3, 0, -3, 0, 1],
]
b = [i[0] for i in A]
# 基向量所在列数，初始基为
baseIndex = [1, 5, 6]
# 目标值
z = 0


def calculateX(baseIndex, z, A, b, c):
    x = []
    for j in range(1, N_cols):
        if j not in baseIndex:
            x.append(0)
        else:
            for i in range(M_rows):
                if A[i][j] == 1:
                    x.append(b[i][0])
    return x


def pivot(baseIndex, A, b, c, z, e, tmpLineIndex):
    for j in baseIndex:
        if A[tmpLineIndex][j] == 1:
            baseIndex.remove(j)
            # 添加入基
    baseIndex.append(e)

    # b[tmpLineIndex] /= A[tmpLineIndex][e]
    for j in range(N_cols):
        A[tmpLineIndex][j] = A[tmpLineIndex][j] / A[tmpLineIndex][e]

    # 高斯行变换
    for i in range(M_rows):
        if i != tmpLineIndex:
            # b[i] = b[i] - A[i][e] * b[tmpLineIndex]
            for j in range(N_cols):
                A[i][j] = A[i][j] - A[i][e] * A[tmpLineIndex][j]

    b = getb(A)

    # C
    for j in range(N_cols):
        c[j] = c[j] - c[e] * A[tmpLineIndex][j]

    z = c[0]
    return baseIndex, A, b, c, z


def initializeDualSimplex(A, b, c, baseIndex):
    global N_cols, M_rows
    # 求B
    B = []

    for i in range(M_rows):
        tmpBLine = []
        for j in range(1, N_cols):
            if j in baseIndex:
                tmpBLine.append(A[i][j])
        B.append(tmpBLine)

    A = np.array(A)
    B = np.array(B)
    b = np.array(getb(A))

    # 求B的逆
    B_1 = inv(B)
    # 左乘B_1
    A = B_1.dot(A)

    cb = []
    for i in range(1, N_cols):
        if i in baseIndex:
            cb.append(c[i])

    cb = np.array(cb)
    c = np.array(c)
    c = c - cb.dot(A)
    z = c[0]

    # print(c,A,sep="\n")
    # exit(1)
    A = A.tolist()
    b = b.tolist()
    c = c.tolist()

    # # 每行末尾添加0
    # for l in A:
    #     l.append(0)
    # A.append([256, 1, 1, 1, 1, 1, 0, 1])
    # c.append(0)
    # M_rows = 4
    # N_cols = 8
    # e = c.index(min(c))
    # tmpIndex = baseIndex
    # baseIndex, A, b, c, z = pivot(baseIndex, A, b, c, z, e, 3)
    # baseIndex = tmpIndex
    # for i in range(1,N_cols):
    #     if i not in baseIndex:
    #         baseIndex.append(i)
    #         break

    # 检查检验数是否都大于0
    while 1:
        for j, val in enumerate(c[1:]):
            if val < 0:
                # 单纯形算法换基，直到c都大于0
                e = j + 1
                tmpLineIndex = ""
                for i in range(M_rows):
                    if A[i][e] > 0:
                        tmpLineIndex = i
                        A = np.array(A)
                        baseIndex, A, b, c, z = pivot(baseIndex, A, b, c, z, e, tmpLineIndex)
                        break
                break
        else:
            # 基向量索引，列表形式的矩阵A，；列表形式的b，列表形式的c，数值型目标函数结果
            return baseIndex, A, b, c, getz(c)


def getb(A):
    return [[i[0]] for i in A]


def getz(c):
    return c[0]


def dual_simplex(baseIndex, z, A, b, c):
    baseIndex, A, b, c, z = initializeDualSimplex(A, getb(A), c, baseIndex)
    while 1:
        tmpLineIndex = 0
        for i in range(len(b)):
            if b[i][0] < 0:
                tmpLineIndex = i
                break
        else:
            x = calculateX(baseIndex, z, A, b, c)
            return x, -z

        d = []
        dIndex = []
        for j in range(1, N_cols):
            if A[tmpLineIndex][j] < 0:
                d.append(-c[j] / A[tmpLineIndex][j])
                dIndex.append(j)
        # 最小步长
        min_d = min(d)
        # 入基向量的列索引
        e = dIndex[d.index(min_d)]
        baseIndex, A, b, c, z = pivot(baseIndex, A, b, c, z, e, tmpLineIndex)


if __name__ == "__main__":
    # 对矩阵A进行预处理
    x, obj = dual_simplex(baseIndex, z, A, b, c)
    # exit(1)
    for i in range(len(x)):
        print("x" + str(i + 1) + ":", x[i])
    print("目标值：", obj)
