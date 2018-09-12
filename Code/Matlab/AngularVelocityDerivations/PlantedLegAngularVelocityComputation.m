syms t1 t2 t3 w1 w2 w3 real;
syms lL lT mL mT mH real;
global params;

% dfeine symbolic variables
params.lL = lL;
params.lT = lT;
params.mL = mL;
params.mH = mH;
params.mT = mT;

% compute speeds after step using symbolic variables
[omegap,omegas,omegat] = computeSpeedsAfterStep(t1,t2,t3,w1,w2,w3) ;

% simplify and print the results
simpSteps = 100;
omp = simplify(omegap,'Criterion','preferReal','Steps',simpSteps)
oms =simplify(omegas,'Criterion','preferReal','Steps',simpSteps)
omt = simplify(omegat,'Criterion','preferReal','Steps',simpSteps)

[numOms,denOms] = numden(oms)

numOms1 = collect(numOms,w1)
numOms2 = collect(numOms1,sin(t1-t2))
numOms3 = collect(numOms2,w1)

omsFin = numOms3/denOms

pretty(omsFin)