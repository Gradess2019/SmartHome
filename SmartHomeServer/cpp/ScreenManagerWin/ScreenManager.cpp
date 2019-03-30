// ScreenManager.cpp : Этот файл содержит функцию "main". Здесь начинается и заканчивается выполнение программы.
//
#include "pch.h"
#include <iostream>
#include <Windows.h>
#include "Winuser.h"
#include "highlevelmonitorconfigurationapi.h"
#include "physicalmonitorenumerationapi.h"

#pragma comment(lib, "Dxva2.lib")

int main(int argc, char* argv[])
{
	if (argc > 2)
	{
		HWND hWnd = GetConsoleWindow();
		HMONITOR hMonitor = NULL;
		DWORD numOfPhysicalMonitors;
		LPPHYSICAL_MONITOR physicalMonitors = NULL;

		hMonitor = MonitorFromWindow(hWnd, MONITOR_DEFAULTTOPRIMARY);

		BOOL bSuccess = GetNumberOfPhysicalMonitorsFromHMONITOR(hMonitor, &numOfPhysicalMonitors);

		if (bSuccess)
		{
			physicalMonitors = (LPPHYSICAL_MONITOR)malloc(numOfPhysicalMonitors * sizeof(PHYSICAL_MONITOR));

			if (physicalMonitors != NULL)
			{
				bSuccess = GetPhysicalMonitorsFromHMONITOR(hMonitor, numOfPhysicalMonitors, physicalMonitors);

				HANDLE monitorHandle = physicalMonitors[0].hPhysicalMonitor;
				DWORD newBrightness = atoi(argv[1]);
				DWORD newContrast = atoi(argv[2]);
				SetMonitorBrightness(monitorHandle, newBrightness);
				SetMonitorContrast(monitorHandle, newContrast);

				free(physicalMonitors);
			}
		}
	}
	
}
